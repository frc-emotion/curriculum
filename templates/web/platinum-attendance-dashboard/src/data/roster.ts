// ============================================================
// RANK:        Web Platinum: Attendance Dashboard
// FILE:        src/data/roster.ts
// STEPS HERE:  none — types and the endpoint
// GUIDE:       GUIDE_URL  (section "Web Platinum")
// RUN:         npm run dev        CHECK: npm run check
// PASSES WHEN: counts update instantly on toggle, loading and error states both
//              show up, and no value is stored in state that could be
//              calculated instead.
// ============================================================
//
// The roster no longer lives in this file. It lives at ROSTER_URL, served from
// public/roster.json — a real HTTP request, which is the whole point: at Gold
// the data was just there, and now you have to go and get it, which means
// waiting, and waiting means loading and error states.

export type Subteam = 'Software' | 'Mechanical' | 'Electrical' | 'Outreach';

export type AttendanceStatus = 'present' | 'absent' | 'excused';

export type Student = {
  id: number;
  name: string;
  grade: number;
  subteam: Subteam;
};

/** Where the roster comes from. Vite serves anything in public/ at the root. */
export const ROSTER_URL = '/roster.json';

/** What every student starts the meeting as. */
export const DEFAULT_STATUS: AttendanceStatus = 'absent';
