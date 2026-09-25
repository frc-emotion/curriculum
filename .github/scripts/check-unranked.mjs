// ============================================================
// The Unranked assessment's checker.
//
// Run from the repo root:  node .github/scripts/check-unranked.mjs <base-sha> <head-sha>
//
// It checks the things a human shouldn't have to: that exactly one member file
// was added, that it is named correctly, that its three headings are filled in,
// and that ROSTER.md gained exactly one row and lost none.
// ============================================================
import { execFileSync } from 'node:child_process';
import { readFileSync } from 'node:fs';

const [baseSha, headSha] = process.argv.slice(2);

if (!baseSha || !headSha) {
  console.error('Usage: node check-unranked.mjs <base-sha> <head-sha>');
  process.exit(1);
}

const problems = [];

function git(args) {
  return execFileSync('git', args, { encoding: 'utf8' });
}

// ------------------------------------------------------------
// 1. Exactly one new member file, named <github-username>.md
// ------------------------------------------------------------
const statuses = git(['diff', '--name-status', `${baseSha}...${headSha}`])
  .split('\n')
  .filter(Boolean)
  .map((line) => {
    const [status, ...paths] = line.split('\t');
    return { status, from: paths[0] ?? '', path: paths[paths.length - 1] ?? '' };
  });

const memberChanges = statuses.filter((change) => change.path.startsWith('unranked/members/'));
const addedMembers = memberChanges.filter(
  (change) => change.status.startsWith('A') && !change.path.endsWith('_TEMPLATE.md')
    && !change.path.endsWith('README.md'),
);
const removedMembers = memberChanges.filter((change) => change.status.startsWith('D'));

if (removedMembers.length > 0) {
  problems.push(
    `This PR deletes ${removedMembers.map((c) => c.path).join(', ')}. Add your own file; don't `
      + 'remove anybody else\'s.',
  );
}

// A rename shows up as R, not A or D — so renaming _TEMPLATE.md to your own name
// would otherwise slip past both checks above.
const renamedMembers = statuses.filter(
  (change) => change.status.startsWith('R') && change.from.startsWith('unranked/members/'),
);

for (const change of renamedMembers) {
  problems.push(
    `STEP 4: this PR renames ${change.from} to ${change.path}. COPY the file instead of `
      + 'renaming it: the original has to stay put for the next person. Put the original '
      + 'back, then add your own file next to it.',
  );
}

if (addedMembers.length === 0) {
  problems.push(
    'STEP 4: I couldn\'t find a new file in unranked/members/. Copy _TEMPLATE.md to '
      + 'unranked/members/<your-github-username>.md and fill it in.',
  );
} else if (addedMembers.length > 1) {
  problems.push(
    `STEP 4: this PR adds ${addedMembers.length} member files. Add just your own.`,
  );
}

for (const member of addedMembers) {
  const name = member.path.replace('unranked/members/', '');

  if (!/^[a-z0-9]([a-z0-9-]*[a-z0-9])?\.md$/.test(name)) {
    problems.push(
      `STEP 4: "${name}" isn't a valid member file name. It has to be your GitHub username in `
        + 'ALL LOWERCASE, ending in .md — for example octocat.md. Capitals work on your laptop '
        + 'and break on Linux, which is what these checks run on.',
    );
    continue;
  }

  const contents = readFileSync(member.path, 'utf8');
  const sections = {
    '# Name': /^#\s+Name\s*$/m,
    "## Track I'm interested in": /^##\s+Track I'm interested in\s*$/m,
    '## Fun fact': /^##\s+Fun fact\s*$/m,
  };

  for (const [heading, pattern] of Object.entries(sections)) {
    const match = pattern.exec(contents);
    if (!match) {
      problems.push(
        `STEP 4: ${name} is missing the "${heading}" heading. Keep the headings exactly as they `
          + 'are in _TEMPLATE.md and write your answer underneath.',
      );
      continue;
    }

    // Everything up to the next heading, with HTML comments removed.
    const after = contents.slice(match.index + match[0].length);
    const body = after.split(/^#{1,6}\s/m)[0] ?? '';
    const text = body.replace(/<!--[\s\S]*?-->/g, '').trim();

    if (text.length === 0) {
      problems.push(
        `STEP 4: the "${heading}" section in ${name} is empty. Write something under it.`,
      );
    }
  }
}

// ------------------------------------------------------------
// 2. ROSTER.md gained exactly one row and lost none
// ------------------------------------------------------------
function rosterRows(ref) {
  let contents;
  try {
    contents = git(['show', `${ref}:unranked/ROSTER.md`]);
  } catch {
    return null;
  }
  return contents
    .split('\n')
    .map((line) => line.trim())
    .filter((line) => line.startsWith('|'))
    // Drop the header row and the |---|---|---| separator.
    .filter((line) => !/^\|[\s:|-]+\|$/.test(line))
    .filter((line) => !/\|\s*Name\s*\|/i.test(line));
}

const before = rosterRows(baseSha);
const after = rosterRows(headSha);

// rosterRows() skips the header and separator, so check them separately: without the
// |---|---|---| line, GitHub stops rendering the table at all.
function hasTableHeader(ref) {
  try {
    const lines = git(['show', `${ref}:unranked/ROSTER.md`]).split('\n').map((l) => l.trim());
    const header = lines.findIndex((line) => /^\|\s*Name\s*\|/i.test(line));
    return header !== -1 && /^\|[\s:|-]+\|$/.test(lines[header + 1] ?? '');
  } catch {
    return true; // A missing file is reported below.
  }
}

if (after !== null && !hasTableHeader(headSha)) {
  problems.push(
    'STEP 5: ROSTER.md lost its "| --- | --- | --- |" line under the header, so the table no '
      + 'longer renders. Put that line back and add your row BELOW it, as a new line.',
  );
}

if (after === null) {
  problems.push('unranked/ROSTER.md is missing. Please put it back.');
} else if (before !== null) {
  const lost = before.filter((row) => !after.includes(row));
  const gained = after.filter((row) => !before.includes(row));

  if (lost.length > 0) {
    problems.push(
      `STEP 5: this PR removes ${lost.length} roster row(s). That is almost always a merge `
        + 'conflict resolved the wrong way: keep BOTH your row and everyone else\'s. Removed: '
        + lost.map((row) => row.slice(0, 60)).join(' / '),
    );
  }

  if (gained.length === 0) {
    problems.push(
      'STEP 5: no new row in unranked/ROSTER.md. Add one with your name, your track and your '
        + 'GitHub username.',
    );
  } else if (gained.length > 1) {
    problems.push(`STEP 5: this PR adds ${gained.length} roster rows. Add just your own.`);
  } else {
    const cells = (gained[0] ?? '').split('|').map((cell) => cell.trim()).filter(Boolean);
    if (cells.length < 3) {
      problems.push(
        `STEP 5: your roster row needs three cells — name, track, GitHub username. Got: `
          + `"${gained[0]}"`,
      );
    }
  }
}

// ------------------------------------------------------------
console.log('');
console.log('============================================================');
if (problems.length === 0) {
  console.log('  Unranked checks passed. Nice work.');
  console.log('');
  console.log('  Your reviewer still checks the rest: clear commit messages, a useful comment');
  console.log('  on a classmate\'s PR, and follow-up commits rather than a force-push.');
  console.log('============================================================');
  process.exit(0);
}

console.log(`  ${problems.length} thing(s) to fix:`);
console.log('');
for (const problem of problems) {
  console.log(`    - ${problem}`);
}
console.log('');
console.log('  Fix them, commit, and push to the same branch — the PR updates itself.');
console.log('============================================================');
process.exit(1);
