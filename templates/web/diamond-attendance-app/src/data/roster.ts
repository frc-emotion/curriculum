// ============================================================
// RANK:        Web Diamond: Attendance App
// FILE:        src/data/roster.ts
// STEPS HERE:  none — the roster and its types
// GUIDE:       GUIDE_URL  (section "Web Diamond")
// RUN:         npx expo start        CHECK: npm run check
// PASSES WHEN: navigation works both ways with params, attendance persists
//              after a restart, and there are no TypeScript errors in the
//              navigation types.
// ============================================================
//
// Finished — don't change it. Same roster you have been using since Copper.

export type Subteam = 'Software' | 'Mechanical' | 'Electrical' | 'Outreach';

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

export const DEFAULT_STATUS: AttendanceStatus = 'absent';

/** The key the attendance map is stored under in AsyncStorage. */
export const ATTENDANCE_STORAGE_KEY = 'attendance:v1';

export function findStudent(studentId: number): Student | undefined {
  return roster.find((student) => student.id === studentId);
}
