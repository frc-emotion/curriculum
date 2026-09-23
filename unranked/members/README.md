# Members

One file per person. This folder is how the team knows who you are.

## Naming your file

Your file must be named:

```
<your-github-username>.md
```

**All lowercase.** If your GitHub username is `OctoCat`, your file is `octocat.md`.

Why lowercase matters: macOS and Windows treat `Octocat.md` and `octocat.md` as the same file,
but Linux — which is what GitHub's servers and our CI run on — treats them as two different
files. Sticking to lowercase avoids a confusing class of bug that is genuinely hard to debug
later. Our CI checks this, so a capital letter will fail your PR.

Do not use spaces, your real name, or `.txt`. Just `<github-username>.md`.

## What goes in it

Copy `_TEMPLATE.md` and fill in all three headings. Leave the headings exactly as they are —
same words, same `#` levels — and write your content underneath each one.

The three required headings:

```markdown
# Name

## Track I'm interested in

## Fun fact
```

- **Name** — your actual name, so leads can match your GitHub account to a human.
- **Track I'm interested in** — `robot` or `web`. Not sure yet? Pick the one that sounds more
  fun. You can switch later; nobody is holding you to it.
- **Fun fact** — one sentence. Anything. This is the easiest part, and CI still checks that
  you filled it in.

## Copying the template

macOS / Linux:

```bash
cp unranked/members/_TEMPLATE.md unranked/members/octocat.md
```

Windows PowerShell:

```powershell
Copy-Item unranked\members\_TEMPLATE.md unranked\members\octocat.md
```

Then open your new file in VS Code and fill it in. Leave `_TEMPLATE.md` alone — the next
person needs it.
