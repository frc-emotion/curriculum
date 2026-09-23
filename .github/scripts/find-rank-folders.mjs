// ============================================================
// Works out which student rank folders a pull request touched, and prints a
// GitHub Actions matrix describing how to check each one.
//
// Run from the repo root:  node .github/scripts/find-rank-folders.mjs <base-sha> <head-sha>
// ============================================================
import { execFileSync } from 'node:child_process';
import { existsSync, readFileSync } from 'node:fs';
import path from 'node:path';

const [baseSha, headSha] = process.argv.slice(2);

if (!baseSha || !headSha) {
  console.error('Usage: node find-rank-folders.mjs <base-sha> <head-sha>');
  process.exit(1);
}

function changedFiles() {
  const output = execFileSync('git', ['diff', '--name-only', `${baseSha}...${headSha}`], {
    encoding: 'utf8',
  });
  return output.split('\n').filter(Boolean);
}

const KNOWN_KINDS = new Set(['java', 'wpilib', 'node', 'vite', 'expo', 'external']);

const folders = new Set();
for (const file of changedFiles()) {
  // students/<username>/<track>/<rank>/...
  const parts = file.split('/');
  if (parts[0] === 'students' && parts.length >= 5) {
    folders.add(parts.slice(0, 4).join('/'));
  }
}

const include = [];
const problems = [];

for (const folder of [...folders].sort()) {
  const rankFile = path.join(folder, '.rank.json');

  if (!existsSync(rankFile)) {
    problems.push(`${folder} has no .rank.json. Use scripts/start-rank.sh to set a rank up.`);
    continue;
  }

  let rank;
  try {
    rank = JSON.parse(readFileSync(rankFile, 'utf8'));
  } catch (error) {
    problems.push(`${folder}/.rank.json isn't valid JSON: ${error.message}`);
    continue;
  }

  if (!KNOWN_KINDS.has(rank.kind)) {
    problems.push(
      `${folder}/.rank.json has kind "${rank.kind}". Expected one of: ${[...KNOWN_KINDS].join(', ')}.`,
    );
    continue;
  }

  // Ranks with no code template are reviewed by a lead, not by CI.
  if (rank.kind === 'external') {
    continue;
  }

  include.push({
    folder,
    kind: rank.kind,
    name: `${rank.student ?? '?'} · ${rank.track ?? '?'} ${rank.rank ?? '?'}`,
  });
}

if (problems.length > 0) {
  for (const problem of problems) {
    console.error(`::error::${problem}`);
  }
  process.exit(1);
}

console.log(JSON.stringify({ include }));
