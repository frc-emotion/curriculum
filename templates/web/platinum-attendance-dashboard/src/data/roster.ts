// ============================================================
// RANK:        Web Platinum: Attendance Dashboard
// STEPS HERE:  none — types and the endpoint
// GUIDE:       https://claude.ai/code/artifact/5fdfb435-43a4-4db4-90f5-c843171497ee  (Web track > Platinum tab)
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
