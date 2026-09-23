# Web Iron — Typed Student Tracker

**Track:** Web (TypeScript / React / React Native)
**Builds on:** Web Copper (JavaScript fundamentals and tooling)

Same roster, same functions — but now TypeScript is watching, and the data comes from a
server instead of a file. Two things you'll do in every project from here on: describe your
data honestly, and don't trust what arrives over the network.

Still no React. One thing at a time.

Guide: **GUIDE_URL** (section "Web Iron")

---

## Skills required

Everything from Copper, plus:

- Type aliases, union types, optional properties
- Typed parameters and return values
- Generics (`<T>`)
- `Record<K, V>` and strict null checks
- `async` / `await` and Promises
- `axios`
- Runtime validation with `zod`
- `try` / `catch` around async code

---

## Setup

Node 24, same as Copper. Then, in this folder:

```bash
npm install
```

---

## Commands

```bash
npm start           # run src/index.ts (tsx compiles TypeScript on the fly)
npm run check       # rank checks + type checker + linter
npm run typecheck   # just the compiler
npm run lint        # just the linter
```

`npm run check` runs all three every time, so you see everything left in one pass rather than
fixing one thing and discovering the next.

---

## The files

```
src/
├── types.ts            step 1 — what IS a student?
├── studentUtils.ts     step 2 — your Copper functions, typed
├── groupBy.ts          step 3 — one generic function
├── api.ts              steps 4-6 — everything that talks to the network
├── index.ts            steps 7-8 — put it together
└── data/students.ts    the roster (annotate it in step 1)
checks/                 the grader. Don't edit it.
```

### Two things that look wrong and aren't

**Imports end in `.js`.** This is an ES module project, so import paths need an extension, and
it's the extension the *output* would have — even though the file on disk is `.ts`:

```ts
import type { Student } from './types.js';
```

**The empty files say `export {};`.** That marks a comment-only file as a module so the rest
of the project can import it. Delete the line once you've exported something real.

---

## Steps

**STEP 1 — `Student` and `Subteam` in `src/types.ts`.**
`Subteam` is a **union** of the four names that appear in the data. `Student` has `contact`
as an **optional** property, with optional `email` and `phone` inside it. Then annotate the
array in `src/data/students.ts` as `Student[]`.
*Checked by: `npm run typecheck`, sourceScan.check.ts.*

**STEP 2 — Port your Copper functions into `src/studentUtils.ts` and type them.**
Same seven names, same behaviour, copied from
`students/<your-username>/web/copper`. Watch which return type TypeScript makes you widen.
*Checked by: studentUtils.check.ts.*

**STEP 3 — `groupBy<T>(items, getKey)` in `src/groupBy.ts`.**

```ts
export function groupBy<T>(
  items: readonly T[],
  getKey: (item: T) => string,
): Record<string, T[]>
```

*Checked by: groupBy.check.ts.*

**STEP 4 — `fetchStudents(url: string): Promise<Student[]>` in `src/api.ts`**, with axios and
`await`.
*Checked by: api.check.ts.*

**STEP 5 — Validate the response with a zod schema before returning it.**
No `as Student[]`. A cast convinces the compiler; it does not check the data.
*Checked by: api.check.ts, sourceScan.check.ts.*

**STEP 6 — Fail cleanly.** A dead server or a 500 throws **your** Error, with a readable
message that names the URL. No raw axios errors escape `api.ts`.
*Checked by: api.check.ts.*

**STEP 7 — Put it together in `src/index.ts`.** Print your utils and your grouping, then call
`fetchStudents`. Point it at `https://jsonplaceholder.typicode.com/users` — that endpoint
works fine and returns *users*, so your schema will refuse it. Watch that happen.
*Checked by: your reviewer.*

**STEP 8 — Handle the failure like a person.** One clear line, no stack trace. Test it with
the wifi off.
*Checked by: your reviewer.*

**STEP 9 — No `any`, no `@ts-ignore`, and `npm run check` fully green.**
*Checked by: the type checker, the linter, sourceScan.check.ts.*

---

## Passes when

- The project compiles in **strict** mode.
- The API data is **validated** before it is used.
- A failed request is handled cleanly.
- No `any` and no `@ts-ignore` anywhere.

---

## Resources

1. [TypeScript for JavaScript Programmers](https://www.typescriptlang.org/docs/handbook/typescript-in-5-minutes.html)
2. [TypeScript Handbook: Everyday Types](https://www.typescriptlang.org/docs/handbook/2/everyday-types.html)
3. [TypeScript Handbook: Generics](https://www.typescriptlang.org/docs/handbook/2/generics.html)
4. [MDN: Using Promises](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Using_promises)
5. [zod: Basic usage](https://zod.dev/basics)

Numbers match the guide's "Web Iron" Resources list. Full guide: **GUIDE_URL**

---

## Stuck?

- **"Module has no exported member 'Student'."** That's step 1 — the type doesn't exist yet.
- **"Object is possibly 'undefined'."** Strict mode found a real hole. Handle the missing
  case; don't reach for `!`.
- **`response.data` is `any`.** That's axios being honest: it can't know. Parsing it with zod
  is what turns it into something you can trust.
- **Your zod schema rejects your own data.** Read the error — usually the optional `contact`
  isn't marked `.optional()`, or the subteam enum is missing a name.
- **Everything compiles but `npm start` explodes.** TypeScript checks shapes at compile time.
  The network is a runtime problem. That gap is the whole reason step 5 exists.
