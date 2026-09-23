// ============================================================
// RANK:        Web Gold: Attendance Card
// FILE:        src/data/roster.ts
// STEPS HERE:  none — this is the data and the types you build on
// GUIDE:       GUIDE_URL  (section "Web Gold")
// RUN:         npm run dev        CHECK: npm run check
// PASSES WHEN: the cards toggle correctly, the filter works, there are no key
//              warnings in the console, and state is never mutated directly.
// ============================================================
//
// Finished — don't change it. The types here are the same shape you wrote
// yourself at Iron, so they should look familiar.

export type Subteam = 'Software' | 'Mechanical' | 'Electrical' | 'Outreach';

/** The three things a student can be at a meeting. */
export type AttendanceStatus = 'present' | 'absent' | 'excused';

export type Student = {
  id: number;
  name: string;
  grade: number;
  subteam: Subteam;
};

export const roster: Student[] = [
  { id: 1, name: 'Ada Nwosu', grade: 11, subteam: 'Software' },
  { id: 2, name: 'Bo Tran', grade: 9, subteam: 'Mechanical' },
  { id: 3, name: 'Chidi Park', grade: 12, subteam: 'Software' },
  { id: 4, name: 'Dara Silva', grade: 10, subteam: 'Electrical' },
  { id: 5, name: 'Emre Kaya', grade: 9, subteam: 'Outreach' },
  { id: 6, name: 'Fen Liu', grade: 11, subteam: 'Mechanical' },
  { id: 7, name: 'Gia Moreno', grade: 10, subteam: 'Software' },
  { id: 8, name: 'Hari Das', grade: 12, subteam: 'Electrical' },
  { id: 9, name: 'Iris Bell', grade: 9, subteam: 'Software' },
  { id: 10, name: 'Jae Sorensen', grade: 10, subteam: 'Outreach' },
  { id: 11, name: 'Kofi Mensah', grade: 11, subteam: 'Mechanical' },
  { id: 12, name: 'Lena Voss', grade: 12, subteam: 'Software' },
];

/** What every student starts the meeting as. */
export const DEFAULT_STATUS: AttendanceStatus = 'absent';
