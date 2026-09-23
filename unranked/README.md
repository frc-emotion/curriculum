<!-- ============================================================
     RANK:        Unranked: Git and Workflows (everyone)
     FILE:        unranked/README.md
     STEPS HERE:  1 to 9
     GUIDE:       GUIDE_URL  (section "Unranked")
     RUN:         (no code to run)   CHECK: opened as a pull request, checked by CI
     PASSES WHEN: the PR is merged, nobody else's roster lines were deleted, and every
                  commit message says what changed.
     ============================================================ -->

# Unranked — Git and Workflows

**Track:** everyone. Robot and web both start here.

**Builds on:** nothing. This is the first rank.

This is the only rank you do by editing this repo directly. Every later rank gets its own
folder under `students/`. Here, you add yourself to the team roster — which means your very
first pull request changes a file other people are also changing. That is the whole point.

Guide: **GUIDE_URL** (section "Unranked")

---

## Skills required

- Installing and configuring Git
- Cloning a repository
- Branches
- Commits and commit messages
- Pushing
- Pull requests
- Reviewing someone else's PR
- Responding to review feedback

You do not need to know any programming for this rank.

---

## Before you start

You need two programs. Both are free.

**Git** — the tool that tracks changes.

- Windows: download from [git-scm.com](https://git-scm.com/download/win). Accept the defaults.
  It installs "Git Bash", which is where you will type Git commands.
- macOS: open Terminal and type `git --version`. If Git is missing, macOS offers to install it.
  Or use [git-scm.com](https://git-scm.com/download/mac).
- Linux: `sudo apt install git` (or your distro's package manager).

**Visual Studio Code** — where you will write code: [code.visualstudio.com](https://code.visualstudio.com/).

Then tell Git who you are. Do this once, ever:

```bash
git config --global user.name "Your Name"
git config --global user.email "your-email@example.com"
```

Use the same email as your GitHub account, or your commits will not be linked to you.

Check it worked:

```bash
git config --global --list
```

---

## Steps

<!-- STEP 1: Install Git and VS Code, and set your Git name and email
     WHAT:       Install Git and Visual Studio Code, then run the two `git config --global`
                 commands above so Git knows your name and email.
     WHY:        Every commit is stamped with a name and email. Without them Git refuses to
                 commit, and on a team we need to know who changed what and why.
     CONCEPTS:   Git installation, global config, identity
     READ:       Guide > Unranked > Resources #1 and #3
     CHECKED BY: your reviewer (your commits will carry your name)
     DONE WHEN:  `git config --global --list` shows your name and email. -->

**1. Install Git and VS Code, and set your Git name and email.**
See "Before you start" above.

<!-- STEP 2: Clone the repo
     WHAT:       Copy this repository onto your own computer with `git clone`.
     WHY:        You never edit code on GitHub's website. You work on your own machine, then
                 send your changes back. A clone is your personal full copy, history and all.
     CONCEPTS:   Remote repositories, cloning, working directory
     READ:       Guide > Unranked > Resources #2
     CHECKED BY: your reviewer
     DONE WHEN:  the repo folder exists on your computer and `git status` works inside it. -->

**2. Clone the repo.**

```bash
git clone <the repo URL from the green Code button>
cd rank-up
```

If your folder path has spaces in it, wrap paths in quotes: `cd "My Folder/rank-up"`.

<!-- STEP 3: Create a branch named unranked/<your-name>
     WHAT:       Make a new branch called `unranked/<your-github-username>` and switch to it.
     WHY:        A branch is your own workspace. Everyone's changes stay separate until they
                 are reviewed, so a mistake in your work can't break anyone else's.
     CONCEPTS:   Branches, HEAD, switching branches
     READ:       Guide > Unranked > Resources #1 and #3
     CHECKED BY: your reviewer (your PR shows the branch name)
     DONE WHEN:  `git status` says "On branch unranked/<your-username>". -->

**3. Create a branch named `unranked/<your-github-username>`.**

```bash
git switch -c unranked/octocat
```

(Use your username, not `octocat`.)

<!-- STEP 4: Add unranked/members/<your-name>.md
     WHAT:       Copy `unranked/members/_TEMPLATE.md` to `unranked/members/<your-github-username>.md`
                 — all lowercase — and fill in all three headings: your name, the track you're
                 interested in, and one fun fact.
     WHY:        This file is how the team knows who you are and which track to point you at.
                 Filling in a template exactly is also most of what following a spec feels like.
     CONCEPTS:   Creating files, Markdown headings, naming conventions
     READ:       Guide > Unranked > Resources #2
     CHECKED BY: CI (file name, and all three headings filled in)
     DONE WHEN:  your file exists, is named in lowercase, and no heading is left empty. -->

**4. Add `unranked/members/<your-github-username>.md`** with your name, the track you're
interested in, and one fun fact. Start from `unranked/members/_TEMPLATE.md` and read
`unranked/members/README.md` for the naming rules.

<!-- STEP 5: Add your name to the shared ROSTER.md file
     WHAT:       Add exactly one row to the table in `unranked/ROSTER.md` with your name, the
                 track you're interested in, and your GitHub username.
     WHY:        This is a shared file. Everyone edits it, which means you will eventually hit
                 a merge conflict here — that is a normal part of working on a team, not a
                 disaster.
     CONCEPTS:   Shared files, Markdown tables, merge conflicts
     READ:       Guide > Unranked > Resources #3
     CHECKED BY: CI (exactly one row added, none removed)
     DONE WHEN:  ROSTER.md has your row and every row that was already there. -->

**5. Add your name to the shared `ROSTER.md` file.** One row. Do not delete anyone else's.

<!-- STEP 6: Commit with a clear message and push your branch
     WHAT:       Stage your changes, commit them with a message that says what changed, and
                 push the branch to GitHub.
     WHY:        Six months from now, "update" tells nobody anything. A good message is a note
                 to your future teammates — and to future you.
     CONCEPTS:   Staging area, commits, commit messages, pushing, remotes
     READ:       Guide > Unranked > Resources #3
     CHECKED BY: your reviewer
     DONE WHEN:  your branch is on GitHub and `git log --oneline` reads like a description. -->

**6. Commit with a clear message and push your branch.**

```bash
git add unranked/members/octocat.md unranked/ROSTER.md
git commit -m "Add octocat to the roster and members folder"
git push -u origin unranked/octocat
```

Good: `Add octocat to the roster and members folder`
Bad: `update`, `stuff`, `asdf`, `fixed it`

<!-- STEP 7: Open a PR into main and fill in the PR template
     WHAT:       Open a pull request from your branch into `main`, and fill in every section of
                 the template that appears.
     WHY:        A PR is how you ask "please look at this before it becomes official." The
                 description is how a reviewer knows what to look for.
     CONCEPTS:   Pull requests, base and compare branches, code review
     READ:       Guide > Unranked > Resources #4
     CHECKED BY: CI (runs automatically on your PR) and your reviewer
     DONE WHEN:  your PR is open against main with every template section filled in. -->

**7. Open a PR into `main` and fill in the PR template.** Do not leave the template empty.

<!-- STEP 8: Leave one useful comment on a classmate's PR
     WHAT:       Find another student's open PR and leave one specific, useful comment.
     WHY:        Reviewing is half the job. Reading other people's changes is also the fastest
                 way to learn what good work looks like.
     CONCEPTS:   Code review, review comments, being useful and kind at the same time
     READ:       Guide > Unranked > Resources #4
     CHECKED BY: your reviewer
     DONE WHEN:  your comment is on someone else's PR and says something specific. -->

**8. Leave one useful comment on a classmate's PR.**

Useful: "Your file is named `Octocat.md` but the README says lowercase — GitHub on Linux
treats those as different files."
Not useful: "looks good", "nice", a thumbs up.

<!-- STEP 9: Address review feedback with a follow-up commit
     WHAT:       When your reviewer asks for a change, make it as a new commit on the same
                 branch and push again.
     WHY:        The conversation and the fix stay connected, so anyone reading later can see
                 what was asked and what you did about it.
     CONCEPTS:   Review cycles, follow-up commits, pushing to an open PR
     READ:       Guide > Unranked > Resources #4
     CHECKED BY: your reviewer
     DONE WHEN:  the feedback is addressed in a new commit and your PR updates automatically. -->

**9. Address any review feedback with a follow-up commit.** Push to the same branch — the PR
updates itself. Do not open a second PR.

---

## Passes when

- Your PR is **merged**.
- Nobody else's roster lines were deleted.
- Every commit message says what changed.

---

## If something goes wrong

**"Your branch is behind main" / merge conflict in ROSTER.md.** Somebody else's row landed
first. This is normal:

```bash
git fetch origin
git merge origin/main
```

Git will mark the conflict in `ROSTER.md` with `<<<<<<<`, `=======` and `>>>>>>>`. Open the
file, keep **both** rows, delete the marker lines, then:

```bash
git add unranked/ROSTER.md
git commit -m "Merge main and keep both roster rows"
git push
```

**I committed to `main` by accident.** Tell a lead. It is fixable and you are not in trouble.

**I pushed the wrong file.** Delete it, commit the deletion, push again. Git history is a log,
not a permanent record of your mistakes.

---

## Resources

1. [Learn Git Branching](https://learngitbranching.js.org/) — an interactive game. Do the first
   few levels; it is the fastest way to make branches click.
2. [GitHub Hello World](https://docs.github.com/en/get-started/start-your-journey/hello-world)
3. [Pro Git: Recording Changes to the Repository](https://git-scm.com/book/en/v2/Git-Basics-Recording-Changes-to-the-Repository)
4. [GitHub: Creating a pull request](https://docs.github.com/en/pull-requests/collaborating-with-pull-requests/proposing-changes-to-your-work-with-pull-requests/creating-a-pull-request)

Full guide: **GUIDE_URL**
