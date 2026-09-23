// ============================================================
// RANK:        Web Iron: Typed Student Tracker
// FILE:        src/data/students.ts
// STEPS HERE:  1 (annotate this array once your Student type exists)
// GUIDE:       GUIDE_URL  (section "Web Iron")
// RUN:         npm start        CHECK: npm run check
// PASSES WHEN: the project compiles in strict mode, the API data is validated
//              before use, and a failed request is handled cleanly.
// ============================================================
//
// The same roster you had at Copper, plus two more people. The data itself is
// finished — the only thing to do here is give it a type (step 1).
//
// Right now TypeScript infers the type from the values, which looks fine until
// somebody adds a student with `subteam: 'Softwear'` and nothing complains.

export const students = [
  { id: 1, name: 'Ada Nwosu', grade: 11, subteam: 'Software', meetingsAttended: 18,
    contact: { email: 'ada@example.com', phone: '555-0101' } },
  { id: 2, name: 'Bo Tran', grade: 9, subteam: 'Mechanical', meetingsAttended: 12,
    contact: { email: 'bo@example.com' } },
  { id: 3, name: 'Chidi Park', grade: 12, subteam: 'Software', meetingsAttended: 20,
    contact: { email: 'chidi@example.com', phone: '555-0103' } },
  { id: 4, name: 'Dara Silva', grade: 10, subteam: 'Electrical', meetingsAttended: 15,
    contact: { email: 'dara@example.com' } },
  { id: 5, name: 'Emre Kaya', grade: 9, subteam: 'Outreach', meetingsAttended: 7 },
  { id: 6, name: 'Fen Liu', grade: 11, subteam: 'Mechanical', meetingsAttended: 15,
    contact: { email: 'fen@example.com', phone: '555-0106' } },
  { id: 7, name: 'Gia Moreno', grade: 10, subteam: 'Software', meetingsAttended: 9,
    contact: { phone: '555-0107' } },
  { id: 8, name: 'Hari Das', grade: 12, subteam: 'Electrical', meetingsAttended: 19,
    contact: { email: 'hari@example.com' } },
  { id: 9, name: 'Iris Bell', grade: 9, subteam: 'Software', meetingsAttended: 4,
    contact: { email: 'iris@example.com' } },
  { id: 10, name: 'Jae Sorensen', grade: 10, subteam: 'Outreach', meetingsAttended: 12,
    contact: { email: 'jae@example.com', phone: '555-0110' } },
  { id: 11, name: 'Kofi Mensah', grade: 11, subteam: 'Mechanical', meetingsAttended: 16,
    contact: { email: 'kofi@example.com' } },
  { id: 12, name: 'Lena Voss', grade: 12, subteam: 'Software', meetingsAttended: 15,
    contact: { email: 'lena@example.com' } },
  { id: 13, name: 'Mira Okafor', grade: 10, subteam: 'Electrical', meetingsAttended: 11,
    contact: { email: 'mira@example.com' } },
  { id: 14, name: 'Noor Haddad', grade: 11, subteam: 'Outreach', meetingsAttended: 16,
    contact: { email: 'noor@example.com', phone: '555-0114' } },
];
