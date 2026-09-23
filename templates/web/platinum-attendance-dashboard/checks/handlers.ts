/* ============================================================
 * The fake server the checks run against, so nothing touches the network.
 * Part of the grader. Don't edit.
 * ============================================================ */
import { HttpResponse, delay, http } from 'msw';
import { setupServer } from 'msw/node';
import { ROSTER_URL, type Student } from '../src/data/roster.ts';

export const ROSTER: Student[] = [
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

export const server = setupServer(http.get(ROSTER_URL, () => HttpResponse.json(ROSTER)));

/** Make the next roster request take a while, so the loading state is visible. */
export function respondSlowly(ms = 150): void {
  server.use(
    http.get(ROSTER_URL, async () => {
      await delay(ms);
      return HttpResponse.json(ROSTER);
    }),
  );
}

/** Make the next roster request fail. */
export function respondWithError(): void {
  server.use(http.get(ROSTER_URL, () => new HttpResponse(null, { status: 500 })));
}
