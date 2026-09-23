#!/usr/bin/env bash
# ============================================================
# start-rank.sh — set up your folder for a rank assessment.
#
#   ./scripts/start-rank.sh <track> <rank> <github-username>
#
# Example:
#   ./scripts/start-rank.sh robot copper octocat
#
# It checks that you finished the rank below this one, makes a branch,
# copies the template into students/<username>/<track>/<rank>/, and tells
# you what to do next. Guide: GUIDE_URL
# ============================================================
set -euo pipefail

FORCE=0
ARGS=()
for arg in "$@"; do
  case "$arg" in
    --force) FORCE=1 ;;
    -h|--help)
      echo "Usage: ./scripts/start-rank.sh <track> <rank> <github-username> [--force]"
      echo "  track: robot | web"
      echo "  rank:  copper | iron | gold | platinum | diamond | emerald | ruby"
      echo "  --force: skip the previous-rank check (leads only)"
      exit 0 ;;
    *) ARGS+=("$arg") ;;
  esac
done

if [ "${#ARGS[@]}" -ne 3 ]; then
  echo "Usage: ./scripts/start-rank.sh <track> <rank> <github-username>"
  echo "Example: ./scripts/start-rank.sh robot copper octocat"
  exit 1
fi

TRACK="$(echo "${ARGS[0]}" | tr '[:upper:]' '[:lower:]')"
RANK="$(echo "${ARGS[1]}" | tr '[:upper:]' '[:lower:]')"
USERNAME="$(echo "${ARGS[2]}" | tr '[:upper:]' '[:lower:]')"

REPO_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "$REPO_ROOT"

# ------------------------------------------------------------
# Validate the arguments
# ------------------------------------------------------------
case "$TRACK" in
  robot|web) ;;
  *) echo "I don't know the track '$TRACK'. It has to be 'robot' or 'web'."; exit 1 ;;
esac

case "$RANK" in
  copper|iron|gold|platinum|diamond|emerald|ruby) ;;
  unranked)
    echo "Unranked is different: you do it by editing this repo directly, not with this script."
    echo "Open unranked/README.md and follow the steps there."
    exit 1 ;;
  *) echo "I don't know the rank '$RANK'."
     echo "Ranks: copper, iron, gold, platinum, diamond, emerald, ruby"
     exit 1 ;;
esac

if ! echo "$USERNAME" | grep -Eq '^[a-z0-9]([a-z0-9-]*[a-z0-9])?$'; then
  echo "'$USERNAME' doesn't look like a GitHub username."
  echo "Use your GitHub username in lowercase, e.g. octocat."
  exit 1
fi

# ------------------------------------------------------------
# Look up this rank
# ------------------------------------------------------------
template_folder() {
  case "$1/$2" in
    robot/copper)   echo "copper-joystick-decider" ;;
    robot/iron)     echo "iron-motor-ramp" ;;
    robot/gold)     echo "gold-mock-robot-hardware" ;;
    robot/platinum) echo "platinum-bench-motor-control" ;;
    robot/diamond)  echo "diamond-simple-motor-subsystem" ;;
    robot/emerald)  echo "emerald-arm-to-presets" ;;
    robot/ruby)     echo "ruby" ;;
    web/copper)     echo "copper-student-filter" ;;
    web/iron)       echo "iron-typed-student-tracker" ;;
    web/gold)       echo "gold-attendance-card" ;;
    web/platinum)   echo "platinum-attendance-dashboard" ;;
    web/diamond)    echo "diamond-attendance-app" ;;
    web/emerald)    echo "emerald" ;;
    web/ruby)       echo "ruby" ;;
  esac
}

rank_kind() {
  case "$1/$2" in
    robot/copper|robot/iron|robot/gold)          echo "java" ;;
    robot/platinum|robot/diamond|robot/emerald)  echo "wpilib" ;;
    web/copper|web/iron)                         echo "node" ;;
    web/gold|web/platinum)                       echo "vite" ;;
    web/diamond)                                 echo "expo" ;;
    *)                                           echo "external" ;;
  esac
}

previous_rank() {
  case "$1" in
    copper)   echo "unranked" ;;
    iron)     echo "copper" ;;
    gold)     echo "iron" ;;
    platinum) echo "gold" ;;
    diamond)  echo "platinum" ;;
    emerald)  echo "diamond" ;;
    ruby)     echo "emerald" ;;
  esac
}

run_command() {
  case "$1" in
    java)     echo "./gradlew run" ;;
    wpilib)   echo "./gradlew simulateJava" ;;
    node)     echo "npm install && npm start" ;;
    vite)     echo "npm install && npm run dev" ;;
    expo)     echo "npm install && npx expo start" ;;
    external) echo "(no code here — read the README)" ;;
  esac
}

check_command() {
  case "$1" in
    java|wpilib) echo "./gradlew rankCheck" ;;
    node|vite|expo) echo "npm run check" ;;
    external) echo "(reviewed by a lead)" ;;
  esac
}

FOLDER="$(template_folder "$TRACK" "$RANK")"
KIND="$(rank_kind "$TRACK" "$RANK")"
PREV="$(previous_rank "$RANK")"
TEMPLATE_DIR="templates/$TRACK/$FOLDER"
DEST_DIR="students/$USERNAME/$TRACK/$RANK"
BRANCH="$USERNAME/$TRACK-$RANK"

if [ ! -d "$TEMPLATE_DIR" ]; then
  echo "I can't find the template at $TEMPLATE_DIR."
  echo "Are you running this from inside the rank-up repo?"
  exit 1
fi

# ------------------------------------------------------------
# Are we in a git repo?
# ------------------------------------------------------------
if ! git rev-parse --is-inside-work-tree >/dev/null 2>&1; then
  echo "This folder isn't a git repository, so I can't make a branch for you."
  echo "Clone the repo with git and run this script from inside the clone."
  exit 1
fi

# Which ref counts as "merged"? Prefer the remote main, fall back to local main.
MAIN_REF=""
for ref in origin/main main; do
  if git rev-parse --verify --quiet "$ref" >/dev/null 2>&1; then MAIN_REF="$ref"; break; fi
done
if [ -z "$MAIN_REF" ]; then
  echo "I couldn't find a 'main' branch to compare against."
  echo "Run 'git fetch origin' and try again."
  exit 1
fi

exists_on_main() { git cat-file -e "$MAIN_REF:$1" 2>/dev/null; }

# ------------------------------------------------------------
# No skipping: check the rank below this one
# ------------------------------------------------------------
if [ "$FORCE" -eq 1 ]; then
  echo "! --force: skipping the previous-rank check. Leads only."
elif [ "$RANK" = "copper" ]; then
  if ! exists_on_main "unranked/members/$USERNAME.md"; then
    cat <<MSG

Hold on — I can't find unranked/members/$USERNAME.md on $MAIN_REF.

Ranks go in order, and Copper builds on Unranked. Before you start Copper, your
Unranked pull request needs to be merged into main. That's the one where you add
your member file and your row in ROSTER.md.

  - Start here:            unranked/README.md
  - Already opened a PR?   It has to be *merged*, not just open.
  - Merged a while ago?    Run 'git fetch origin' so your clone can see it.
  - Different username?    Use the same one as your member file.

MSG
    exit 1
  fi
else
  if ! git ls-tree -d --name-only "$MAIN_REF" "students/$USERNAME/$TRACK/$PREV" 2>/dev/null | grep -q .; then
    cat <<MSG

Hold on — I can't find students/$USERNAME/$TRACK/$PREV/ on $MAIN_REF.

Ranks go in order, and $TRACK $RANK builds directly on $TRACK $PREV. Each rank
assumes you already know everything below it, so there's no skipping ahead.

  - Finish $TRACK $PREV first and get that pull request merged into main.
  - Already merged?  Run 'git fetch origin' so your clone can see it.
  - Wrong track?     You're asking for the '$TRACK' track.

MSG
    exit 1
  fi
fi

# ------------------------------------------------------------
# Make the branch
# ------------------------------------------------------------
if [ -n "$(git status --porcelain)" ]; then
  echo "Heads up: you have uncommitted changes. Commit or stash them first so they"
  echo "don't get mixed into your new rank branch."
  exit 1
fi

if git rev-parse --verify --quiet "$BRANCH" >/dev/null 2>&1; then
  echo "You already have a branch called $BRANCH. Switching to it."
  git switch "$BRANCH"
else
  git switch -c "$BRANCH" "$MAIN_REF" >/dev/null 2>&1 || git switch -c "$BRANCH"
  echo "Created branch $BRANCH"
fi

# ------------------------------------------------------------
# Copy the template
# ------------------------------------------------------------
if [ -d "$DEST_DIR" ]; then
  echo
  echo "$DEST_DIR already exists, so I left it alone — your work is safe."
  echo "Delete that folder yourself if you really want a fresh copy."
else
  mkdir -p "$DEST_DIR"
  # -a keeps file permissions, so gradlew stays executable.
  cp -a "$TEMPLATE_DIR/." "$DEST_DIR/"
  echo "Copied $TEMPLATE_DIR -> $DEST_DIR"
fi

cat > "$DEST_DIR/.rank.json" <<JSON
{
  "track": "$TRACK",
  "rank": "$RANK",
  "student": "$USERNAME",
  "kind": "$KIND"
}
JSON

# ------------------------------------------------------------
# Tell them what to do next
# ------------------------------------------------------------
cat <<NEXT

------------------------------------------------------------
  You're set up for $TRACK $RANK.
------------------------------------------------------------

Your folder:   $DEST_DIR
Your branch:   $BRANCH

Next:

  1. Read the README in your folder, start to finish:
       $DEST_DIR/README.md

  2. Run it once before you change anything:
       cd "$DEST_DIR"
       $(run_command "$KIND")

  3. Run the check. It is SUPPOSED to fail right now — the failures are your to-do list:
       $(check_command "$KIND")

  4. Work through the STEP comments in order. Commit as you go:
       git add "$DEST_DIR"
       git commit -m "$TRACK $RANK: step 1"

  5. When the check passes, push and open a pull request into main:
       git push -u origin $BRANCH

Work only inside $DEST_DIR. Don't edit anyone else's folder or the templates.

Guide: GUIDE_URL
NEXT
