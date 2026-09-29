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
1. Open VS Code (it comes preinstalled; hit Windows, type VS Code, hit enter. If you have a personal PC, search up VS Code and install it).
2. Open the terminal. The shortcut for opening the terminal once you're on VS Code is `ctrl + ~`.

Then tell Git who you are. This is saved globally, so this is like one-time thing you have to run and never again in the future. Run this (replace your name with your actual name, and the email with your GitHub email):

```bash
git config --global user.name "Your Name"
git config --global user.email "your-email@example.com"
```

Check it worked:

```bash
git config --global --list
```

---

## Steps
<!-- STEP 2: Clone the repo
     WHAT:       Copy this repository onto your own computer with `git clone`.
     WHY:        You never edit code on GitHub's website. You work on your own machine, then
                 send your changes back. A clone is your personal full copy, history and all.
     CONCEPTS:   Remote repositories, cloning, working directory
     READ:       Guide > Unranked > Resources #2
     CHECKED BY: your reviewer
     DONE WHEN:  the repo folder exists on your computer and `git status` works inside it. -->

**1. Clone the repo.**
After these steps, you should still be in VS Code, with the terminal open.

**Paste this:** 
```bash
git clone
```

You should get an error. *Why?* 
This is because when you run `git clone`, you must also pass in the URL of the GitHub repository you actually want to clone. Pasting the link tells Git *where* to download the files from.

For the curriculum, you have to clone this GitHub repository itself. Hit `<> Code` on the top left, hit code, and copy the link. This is **very important**. This process is the same for every repository to copy their cloning link.

<img width="996" height="419" alt="image" src="https://github.com/user-attachments/assets/b9220ecd-056a-42c6-8357-8911908c26fb" />

So, rerun `git clone URL`, but replace `URL` with the actual link you got. This should run successfully.

**2. Open up the repo.**
You have the project downloaded on your computer now. Now, you need to tell VS Code to open up this folder that you just cloned.

The name of the cloned folder is **always** the name of the **GitHub repository that you cloned** itself.

First, you must change the terminal's **directory** to where the cloned folder is. 
- Do you notice that when you have the terminal open, on each line, it has a line of text like `C:\Users\1912409`? (it might look a little different, but that's fine)
- That is called the **directory** path. A directory is just a folder.

All commands you run are run inside the directory that you are in. You want to work on `curriculum` folder, so run:
```bash
cd curriculum
```
*Note: `cd` just stands for "change directory." After the space, put in the folder path you want to navigate to, in this case, the path is just the folder name (which is the name of the GitHub repo)*

Now, you have navigated to that directory path. The first lines of the terminal should now look something like `C:\Users\1912409\curriculum`. 

Then, run:
```bash
code .
```

`code` is a command provided by the VS Code app itself. All you need to know is that this command opens up the current directory you have open in your terminal visually onto VS Code.

<!-- STEP 3: Create a branch named unranked/<your-name>
     WHAT:       Make a new branch called `unranked/<your-github-username>` and switch to it.
     WHY:        A branch is your own workspace. Everyone's changes stay separate until they
                 are reviewed, so a mistake in your work can't break anyone else's.
     CONCEPTS:   Branches, HEAD, switching branches
     READ:       Guide > Unranked > Resources #1 and #3
     CHECKED BY: your reviewer (your PR shows the branch name)
     DONE WHEN:  `git status` says "On branch unranked/<your-username>". -->

**3. Create a branch.**
A branch is just like a parallel timeline used to store your independent changes, while leaving the main repository intact.
- The purpose of this is **isolation**. You can test features to test for bugs before fully pushing it to main.

Create a branch like this (replace **GITHUBUSERNAME** with your actual GitHub username. If we see a branch actually called "GITHUBUSERNAME" we will personally pulverize it cause it means you're not reading instructions):

```bash
git switch -c unranked/GITHUBUSERNAME
```

`git switch` changes the branch. Adding `-c` just means to create the branch if it doesn't already exist.

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

**4. Create a new file, titled with your GitHub username** 
Add a new file titled `GITHUBUSERNAME.md` with your name in the **members** folder inside of the **unranked** folder. 
Much like how `.txt` file is a text file, `.md` just means that it's a markdown file (basically regular text but fancier)

Inside of this file, just type the track that you're interested in, and one fun fact.

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
First, you have to tell Git to **add** your changes to the staging area. This is basically the docking area where changes go before being formally committed. 

This is achieved by running `git add`, and the file paths where changes were made.
```bash
git add unranked/members/GITHUBUSERNAME.md
git add unranked/members/GITHUBUSERNAME.md
```

Now, your changes are ready to be formally committed as points on a timeline. You achieve this with `git commit -m ""`. The empty quotations is used to hold the **commit message**. This is basically a message talking about what the change is, so you can easily understand what each change did in the future.
- Because you'll reference it in the future, there's no guarantee that you will remember what each change does. As a result, try to make your commit messages as **detailed** as possible.
- In this case you're just adding your name, but keep this practice in mind. It will haunt you if you don't.

```bash
git commit -m "added my name!!!"
```

Okay, now your changes are committed, but **on your computer**. In order to push the change onto the server (Github) from your computer, you tell Git to **"push"** the changes you made.

```bash
git push -u origin unranked/GITHUBUSERNAME
```

<!-- STEP 7: Open a PR into main and fill in the PR template
     WHAT:       Open a pull request from your branch into `main`, and fill in every section of
                 the template that appears.
     WHY:        A PR is how you ask "please look at this before it becomes official." The
                 description is how a reviewer knows what to look for.
     CONCEPTS:   Pull requests, base and compare branches, code review
     READ:       Guide > Unranked > Resources #4
     CHECKED BY: CI (runs automatically on your PR) and your reviewer
     DONE WHEN:  your PR is open against main with every template section filled in. -->

**7. Open a PR into `main`** 
Now that you created and pushed the change, you have to ask for permission to formally merge your separate timeline back onto the main branch. You achieve this with a **pull request**.

Go back to the repository home page, which is the place you see when you hit `<> Code`. 

There should be a button on the top that asks you to create a pull request. All the instructions for the pull request are self-explanatory and shown by GitHub itself. Reference Week 1 slides if you don't understand. 


<!-- STEP 8: Leave one useful comment on a classmate's PR
     WHAT:       Find another student's open PR and leave one specific, useful comment.
     WHY:        Reviewing is half the job. Reading other people's changes is also the fastest
                 way to learn what good work looks like.
     CONCEPTS:   Code review, review comments, being useful and kind at the same time
     READ:       Guide > Unranked > Resources #4
     CHECKED BY: your reviewer
     DONE WHEN:  your comment is on someone else's PR and says something specific. -->

---


## If something goes wrong

Get a lead to come help you. If you think you're done, ask a lead to check your work.
