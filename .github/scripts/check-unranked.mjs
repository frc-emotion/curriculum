// ============================================================
// The Unranked assessment's checker.
//
// Run from the repo root:  node .github/scripts/check-unranked.mjs <base-sha> <head-sha>
//
// Two kinds of result:
//
//   BLOCKERS  fail the check. Only things that damage work other people depend on:
//             deleting or renaming someone else's member file or the template,
//             removing roster rows, or breaking the roster table.
//
//   NOTES     print a warning but let the check pass. Everything about the student's
//             own work: file name, headings, how many rows they added. The reviewer
//             reads these and decides — CI doesn't gatekeep unfinished work.
// ============================================================
import { execFileSync } from 'node:child_process';
import { appendFileSync } from 'node:fs';

const [baseSha, headSha] = process.argv.slice(2);

if (!baseSha || !headSha) {
  console.error('Usage: node check-unranked.mjs <base-sha> <head-sha>');
  process.exit(1);
}

const blockers = [];
const notes = [];

function git(args) {
  return execFileSync('git', args, { encoding: 'utf8' });
}

// Read files from the PR's commit, not the disk, so this works from any checkout.
function readAt(ref, file) {
  try {
    return git(['show', `${ref}:${file}`]);
  } catch {
    return null;
  }
}

// ------------------------------------------------------------
// 1. Member files
// ------------------------------------------------------------
const statuses = git(['diff', '--name-status', `${baseSha}...${headSha}`])
  .split('\n')
  .filter(Boolean)
  .map((line) => {
    const [status, ...paths] = line.split('\t');
    return { status, from: paths[0] ?? '', path: paths[paths.length - 1] ?? '' };
  });

const inMembers = (file) => file.startsWith('unranked/members/');
const isShared = (file) => file.endsWith('/_TEMPLATE.md') || file.endsWith('/README.md');

// Deleting or renaming a file that was already on main takes it away from everyone.
for (const change of statuses) {
  if (change.status.startsWith('D') && inMembers(change.path)) {
    blockers.push(
      `This PR deletes ${change.path}. Put it back — add your own file, don't remove one that `
        + 'was already there.',
    );
  }
  if (change.status.startsWith('R') && inMembers(change.from)) {
    blockers.push(
      `This PR renames ${change.from} to ${change.path}. COPY the file instead of renaming it, `
        + 'so the original stays put for the next person.',
    );
  }
}

const addedMembers = statuses.filter(
  (change) => (change.status.startsWith('A') || change.status.startsWith('R'))
    && inMembers(change.path) && !isShared(change.path),
);

if (addedMembers.length === 0) {
  notes.push(
    'STEP 4: no new file in unranked/members/. Copy _TEMPLATE.md to '
      + 'unranked/members/<your-github-username>.md and fill it in.',
  );
} else if (addedMembers.length > 1) {
  notes.push(`STEP 4: this PR adds ${addedMembers.length} member files. Usually it's just your own.`);
}

for (const member of addedMembers) {
  const name = member.path.replace('unranked/members/', '');

  if (!/^[a-z0-9]([a-z0-9-]*[a-z0-9])?\.md$/.test(name)) {
    notes.push(
      `STEP 4: "${name}" should be your GitHub username in all lowercase, ending in .md — `
        + 'for example octocat.md.',
    );
  }

  const contents = readAt(headSha, member.path) ?? '';
  const withoutComments = contents.replace(/<!--[\s\S]*?-->/g, '');
  const sections = {
    '# Name': /^#\s+Name\s*$/m,
    "## Track I'm interested in": /^##\s+Track I'm interested in\s*$/m,
    '## Fun fact': /^##\s+Fun fact\s*$/m,
  };

  if (withoutComments.trim().length === 0) {
    notes.push(`STEP 4: ${name} is empty. Fill in your name, track and a fun fact.`);
    continue;
  }

  const missing = Object.keys(sections).filter((heading) => !sections[heading].test(withoutComments));
  if (missing.length > 0) {
    notes.push(
      `STEP 4: ${name} changed the template's headings (missing: ${missing.join(', ')}). Keep `
        + 'each heading exactly as it is and write your answer on the line UNDER it.',
    );
    continue;
  }

  for (const [heading, pattern] of Object.entries(sections)) {
    const match = pattern.exec(withoutComments);
    const body = withoutComments.slice(match.index + match[0].length).split(/^#{1,6}\s/m)[0];
    if (body.trim().length === 0) {
      notes.push(`STEP 4: the "${heading}" section in ${name} is still empty.`);
    }
  }
}

// ------------------------------------------------------------
// 2. ROSTER.md
// ------------------------------------------------------------
const ROSTER = 'unranked/ROSTER.md';
const isSeparator = (line) => /^\|[\s:|-]+\|$/.test(line);
const isHeader = (line) => /^\|\s*Name\s*\|/i.test(line);
// Collapse spacing so "|a|b|" and "| a | b |" count as the same row.
const normalize = (row) => row.split('|').map((cell) => cell.trim()).join('|');

function rosterRows(contents) {
  return contents
    .split('\n')
    .map((line) => line.trim())
    .filter((line) => line.startsWith('|') && !isSeparator(line) && !isHeader(line));
}

function hasTableHeader(contents) {
  const lines = contents.split('\n').map((line) => line.trim());
  const header = lines.findIndex(isHeader);
  return header !== -1 && isSeparator(lines[header + 1] ?? '');
}

const rosterBefore = readAt(baseSha, ROSTER);
const rosterAfter = readAt(headSha, ROSTER);

if (rosterAfter === null) {
  blockers.push(`${ROSTER} is missing. Please put it back.`);
} else {
  if (!hasTableHeader(rosterAfter)) {
    blockers.push(
      'STEP 5: ROSTER.md lost its "| --- | --- | --- |" line under the header, so the table '
        + 'no longer renders for anyone. Put that line back and add your row BELOW it.',
    );
  }

  if (rosterBefore !== null) {
    const before = rosterRows(rosterBefore);
    const after = rosterRows(rosterAfter);
    const afterSet = new Set(after.map(normalize));
    const beforeSet = new Set(before.map(normalize));
    const lost = before.filter((row) => !afterSet.has(normalize(row)));
    const gained = after.filter((row) => !beforeSet.has(normalize(row)));

    if (lost.length > 0) {
      blockers.push(
        `STEP 5: this PR removes ${lost.length} roster row(s) that someone else added. That is `
          + 'almost always a merge conflict resolved the wrong way: keep BOTH your row and '
          + `everyone else's. Removed: ${lost.map((row) => row.slice(0, 60)).join(' / ')}`,
      );
    }

    if (gained.length === 0) {
      notes.push('STEP 5: no new row in ROSTER.md yet. Add one: | name | robot or web | username |');
    } else if (gained.length > 1) {
      notes.push(`STEP 5: this PR adds ${gained.length} roster rows. Usually it's just your own.`);
    } else {
      const cells = gained[0].split('|').map((cell) => cell.trim()).filter(Boolean);
      if (cells.length !== 3 || !gained[0].endsWith('|')) {
        notes.push(
          `STEP 5: your roster row should be three cells with a | on each end — `
            + `| name | robot or web | username |. Got: "${gained[0]}"`,
        );
      }
    }
  }
}

// ------------------------------------------------------------
// Report
// ------------------------------------------------------------
for (const note of notes) console.log(`::warning title=Unranked note::${note}`);
for (const blocker of blockers) console.log(`::error title=Unranked blocker::${blocker}`);

if (process.env.GITHUB_STEP_SUMMARY) {
  const lines = ['## Unranked check', ''];
  if (blockers.length) lines.push('**Must fix before merging:**', ...blockers.map((b) => `- ${b}`), '');
  if (notes.length) lines.push('**Notes for the reviewer (not blocking):**', ...notes.map((n) => `- ${n}`), '');
  if (!blockers.length && !notes.length) lines.push('Everything the checker looks at is in order.');
  appendFileSync(process.env.GITHUB_STEP_SUMMARY, `${lines.join('\n')}\n`);
}

console.log('');
console.log('============================================================');
if (blockers.length === 0) {
  console.log(notes.length === 0
    ? '  Unranked checks passed. Nice work.'
    : `  Passing, with ${notes.length} note(s) for you and your reviewer above.`);
  console.log('');
  console.log('  Your reviewer still checks the rest: clear commit messages, a useful comment');
  console.log('  on a classmate\'s PR, and follow-up commits rather than a force-push.');
  console.log('============================================================');
  process.exit(0);
}

console.log(`  ${blockers.length} thing(s) to fix — these change files other people depend on:`);
console.log('');
for (const blocker of blockers) console.log(`    - ${blocker}`);
console.log('');
console.log('  Fix them, commit, and push to the same branch — the PR updates itself.');
console.log('============================================================');
process.exit(1);
