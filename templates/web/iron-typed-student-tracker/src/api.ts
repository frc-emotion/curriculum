// ============================================================
// RANK:        Web Iron: Typed Student Tracker
// FILE:        src/api.ts
// STEPS HERE:  4, 5, 6
// GUIDE:       GUIDE_URL  (section "Web Iron")
// RUN:         npm start        CHECK: npm run check
// PASSES WHEN: the project compiles in strict mode, the API data is validated
//              before use, and a failed request is handled cleanly.
// ============================================================
//
// Everything that talks to the network lives in this file. That is a rule worth
// keeping for life: components and utilities should not know where data comes
// from, so that when the endpoint changes there is one file to edit.
//
// nautilus-frontend does exactly this, with axios, which is why you are using
// axios here rather than plain fetch.
//
// Imports you will need as you go (add them yourself):
//   import axios from 'axios';
//   import { z } from 'zod';
//   import type { Student } from './types.js';

// STEP 4: Fetch the students
// WHAT:       Export an async function with exactly this signature:
//               export async function fetchStudents(url: string): Promise<Student[]>
//             It requests the URL with axios and returns the list of students.
//             Use async/await, not .then() chains.
// WHY:        Every app you will ever write waits on a server. `await` is how
//             you write that waiting so it reads top to bottom instead of
//             nesting callbacks. And taking the URL as a parameter — rather than
//             hard-coding it — is what makes the next two steps testable without
//             a real server.
// CONCEPTS:   async/await, Promises, axios, typed return values
// READ:       Guide > Web Iron > Resources #4
// CHECKED BY: api.check.ts
// DONE WHEN:  fetchStudents resolves to an array of students from a live URL.

// STEP 5: Validate the response before you trust it
// WHAT:       Write a zod schema describing what the API is supposed to send —
//             an array of students, matching your Student type — and run the
//             response through it before returning. If the data does not match,
//             throw an Error whose message says the data was invalid.
//             Do NOT cast the response with `as Student[]`.
// WHY:        `response.data` is typed `any` by axios, which means TypeScript
//             believes whatever you tell it. A cast is a promise you cannot
//             keep: the server is a different program, maybe a different team,
//             maybe mid-deploy. zod checks at RUNTIME, when the data actually
//             arrives, and that is the only moment the truth is available.
//             This is exactly what nautilus-frontend does with its API
//             responses.
// CONCEPTS:   Runtime validation, zod schemas, parse vs safeParse, why a type
//             assertion is not a check, trust boundaries
// READ:       Guide > Web Iron > Resources #5
// CHECKED BY: api.check.ts (a response with a bad subteam must be rejected)
// DONE WHEN:  a response where one student has subteam 'Softwear' throws,
//             instead of quietly returning bad data.

// STEP 6: Fail cleanly
// WHAT:       When the request itself fails — server down, 500, no network —
//             catch it and throw an Error with a short, human-readable message
//             that mentions the URL. Never let a raw axios error escape this
//             file.
// WHY:        An axios error is a 40-line object describing sockets. It is
//             useless to a user and nearly useless to you. The job of this
//             layer is to turn "the internet happened" into one sentence the
//             rest of the app can show someone.
// CONCEPTS:   try/catch with async, error handling, rethrowing, error messages
//             as a user interface
// READ:       Guide > Web Iron > Resources #4
// CHECKED BY: api.check.ts (a 500 and a network error must both throw a clean
//             Error mentioning the URL)
// DONE WHEN:  a dead URL produces one readable sentence, not a stack trace.

// This line makes the file a module while it is still empty, so the rest of the
// project can import it. Once you have exported something real, you can delete it.
export {};
