# Web Copper — Student Filter

**Track:** Web (TypeScript / React / React Native)
**Builds on:** Unranked (Git and Workflows)

Seven small functions over the team roster. No framework, no build step, no browser — just
JavaScript and the array methods you'll use every single day from here on.

One rule runs through the whole rank: **never change the data you were given**. The checks
freeze the roster, so you'll find out immediately if you try. That rule exists because two
ranks from now, in React, changing data in place is the single most common reason a screen
refuses to update.

Guide: **GUIDE_URL** (section "Web Copper")

---

## Skills required

- `const` and `let` (and why not `var`)
- Arrow functions
- `.map`, `.filter`, `.find`, `.reduce`, `.sort`
- Objects and destructuring
- Template literals
- Optional chaining `?.` and nullish coalescing `??`
- ES modules: `import` and `export`
- npm scripts, and what a linter is for

---

## Setup

You need **Node.js 24**. Check what you have:

```bash
node --version
```

If it's older than 22.12, install Node 24 from [nodejs.org](https://nodejs.org/) (take the
LTS download). If you use `nvm`, this repo has a `.nvmrc`, so `nvm use` picks the right one.

Then, **inside this folder**:

```bash
npm install
```

That downloads the tools into `node_modules/`. It only needs doing once, and `node_modules`
is never committed — that's what `.gitignore` is for.

---

## Commands

```bash
npm start       # run src/index.js and print things
npm run check   # grade your work: the rank checks AND the linter
npm run lint    # just the linter
```

`npm run check` fails right now, on purpose. Every failure starts with a step number:

```
STEP 3: there is no student 99, so findStudentById should give back undefined.
```

That's your to-do list. Work the lowest number first.

---

## The files

```
src/
├── students.js       the roster — finished, don't change it
├── studentUtils.js   steps 1-7 — your seven functions
└── index.js          step 8 — print things and look at them
checks/               the grader. Don't edit it.
```

Read `src/students.js` before you start. Three things in there matter: two students are tied
on attendance, one has no `contact` at all, and one has a `contact` with no email in it.
Those aren't mistakes — they're steps 5 and 7.

---

## Steps

**STEP 1 — `getStudentNames(students)`** → array of names, same order. (`.map`)

**STEP 2 — `getStudentsBySubteam(students, subteam)`** → the matching students as whole
objects. Empty array when nobody matches. (`.filter`)

**STEP 3 — `findStudentById(students, id)`** → one student, or `undefined`. (`.find`)

**STEP 4 — `countByGrade(students)`** → `{ 9: 3, 10: 3, 11: 3, 12: 3 }`. Only grades that
actually appear.

**STEP 5 — `getRegularAttendees(students, minimum)`** → students with at least `minimum`
meetings, most first, **ties broken alphabetically by name**. Don't sort the array you were
given — `.sort()` changes it in place.

**STEP 6 — `formatStudent(student)`** → `Ada Nwosu (Grade 11, Software)`. Use a
**destructured parameter** and a template literal.

**STEP 7 — `getContactEmail(student)`** → the email, or `'no email on file'`. Must not crash
when there's no `contact`. Use `?.` and `??`. (Think about what `||` would do to an empty
string.)

**STEP 8 — Print everything in `src/index.js`**, with labels, and run `npm start`.
*Checked by: your reviewer.*

**STEP 9 — Make the linter happy.** No `var`, no `==`, no unused variables.
*Checked by: `npm run lint`, which `npm run check` runs for you.*

**STEP 10 — Get `npm run check` fully green.**

Every function must be **exported** by exactly the name above — the checks import them by
name.

---

## Passes when

- All checks pass.
- There is no `var` and no `==` anywhere.
- The original `students` array is never modified.
- `npm start` prints a labelled result for all seven functions.

---

## Resources

1. [MDN: JavaScript first steps](https://developer.mozilla.org/en-US/docs/Learn_web_development/Core/Scripting)
2. [MDN: Working with objects](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Working_with_objects)
3. [MDN: Array methods](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Array)
4. [MDN: Optional chaining](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Operators/Optional_chaining)

Numbers match the guide's "Web Copper" Resources list. Full guide: **GUIDE_URL**

---

## Stuck?

- **"I couldn't find an exported function called..."** — either the name is spelled
  differently, or the line doesn't start with `export`.
- **"tried to change the array it was given"** — copy it first. `[...students]` gives you a
  new array with the same contents.
- **`.sort()` keeps reordering the roster.** That's exactly what it does; it sorts in place.
  Copy, then sort the copy.
- **Ties come out in a different order each run.** Your comparator returns 0 for ties, so the
  order is whatever the sort left behind. Compare names when the counts match.
- **`getContactEmail` returns the fallback for an empty email.** You used `||` instead of
  `??`. `||` treats `''`, `0` and `false` as missing; `??` only treats `null` and `undefined`
  that way.
