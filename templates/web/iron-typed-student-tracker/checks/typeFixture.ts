/* ============================================================
 * This file exists so that `tsc --noEmit` fails until your types are real.
 *
 * It does nothing at runtime. It just uses `Student` and `Subteam` the way the
 * rest of the project will, so the type checker has something to complain
 * about while they are still missing.
 *
 * Part of the grader. Don't edit.
 * ============================================================ */
import type { Student, Subteam } from '../src/types.js';

const exampleSubteam: Subteam = 'Software';

const exampleStudent: Student = {
  id: 1,
  name: 'Ada Nwosu',
  grade: 11,
  subteam: exampleSubteam,
  meetingsAttended: 18,
  contact: { email: 'ada@example.com', phone: '555-0101' },
};

// A student with no contact at all has to be legal, or the optional property is
// not actually optional.
const withoutContact: Student = {
  id: 5,
  name: 'Emre Kaya',
  grade: 9,
  subteam: 'Outreach',
  meetingsAttended: 7,
};

export const typeFixtures = [exampleStudent, withoutContact];
