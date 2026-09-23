// ============================================================
// RANK:        Web Copper: Student Filter
// FILE:        src/students.js
// STEPS HERE:  none — this is the data you work on
// GUIDE:       GUIDE_URL  (section "Web Copper")
// RUN:         npm start        CHECK: npm run check
// PASSES WHEN: all checks pass, there is no `var` or `==`, and the original
//              students array is never modified.
// ============================================================
//
// The team roster. This file is finished — don't change it.
//
// Read it before you start. Notice:
//   - two people share the same meetingsAttended count (that's a tie, and step 5
//     has an opinion about ties)
//   - one person has no `contact` at all
//   - one person has a `contact` with no `email` in it
//
// Those last two are not mistakes. They are step 7.

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
];
