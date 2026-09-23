import { describe, expect, it } from 'vitest';
import { call, frozenStudents } from './_support.js';

describe('STEP 2: getStudentsBySubteam', () => {
  it('returns only that subteam, as whole student objects', () => {
    const software = call(2, 'getStudentsBySubteam', frozenStudents(), 'Software');

    expect(Array.isArray(software), 'STEP 2: getStudentsBySubteam should return an array.').toBe(true);
    expect(software.length,
      'STEP 2: there are 5 students on Software, so that\'s how many should come back.').toBe(5);
    expect(software.every((student) => student.subteam === 'Software'),
      'STEP 2: one of the students you returned isn\'t on Software.').toBe(true);
    expect(typeof software[0],
      'STEP 2: return the student objects themselves, not just their names.').toBe('object');
    expect(software[0].name,
      'STEP 2: the objects should be the full students, with a name on them.').toBeDefined();
  });

  it('works for the other subteams too', () => {
    expect(call(2, 'getStudentsBySubteam', frozenStudents(), 'Mechanical').length,
      'STEP 2: there are 3 students on Mechanical.').toBe(3);
    expect(call(2, 'getStudentsBySubteam', frozenStudents(), 'Electrical').length,
      'STEP 2: there are 2 students on Electrical.').toBe(2);
    expect(call(2, 'getStudentsBySubteam', frozenStudents(), 'Outreach').length,
      'STEP 2: there are 2 students on Outreach.').toBe(2);
  });

  it('returns an empty array when nobody matches', () => {
    const none = call(2, 'getStudentsBySubteam', frozenStudents(), 'Robotics');
    expect(none,
      'STEP 2: nobody is on "Robotics", so return an empty array — not null, not undefined. '
      + 'Code that reads this result shouldn\'t have to check which kind of nothing it got.').toEqual([]);
  });

  it('leaves the roster alone', () => {
    const roster = frozenStudents();
    call(2, 'getStudentsBySubteam', roster, 'Software');
    expect(roster.length, 'STEP 2: getStudentsBySubteam changed the roster it was given.').toBe(12);
  });
});
