import { describe, expect, it } from 'vitest';
import { call, frozenStudents } from './_support.js';

describe('STEP 4: countByGrade', () => {
  it('counts how many students are in each grade', () => {
    const counts = call(4, 'countByGrade', frozenStudents());

    expect(typeof counts, 'STEP 4: countByGrade should return an object.').toBe('object');
    expect(counts,
      'STEP 4: the roster has 3 students in each of grades 9, 10, 11 and 12.')
      .toEqual({ 9: 3, 10: 3, 11: 3, 12: 3 });
  });

  it('adds up to the number of students', () => {
    const counts = call(4, 'countByGrade', frozenStudents());
    const total = Object.values(counts).reduce((sum, count) => sum + count, 0);
    expect(total,
      'STEP 4: every student belongs to exactly one grade, so the counts should add up to 12.').toBe(12);
  });

  it('gives an empty object for an empty roster', () => {
    expect(call(4, 'countByGrade', []),
      'STEP 4: with nobody on the roster there are no grades to count, so the answer is {}.').toEqual({});
  });

  it('only includes grades that actually appear', () => {
    const counts = call(4, 'countByGrade', [
      { id: 1, name: 'Solo', grade: 11, subteam: 'Software', meetingsAttended: 1 },
    ]);
    expect(counts,
      'STEP 4: only grades that appear should be in the object. Don\'t add 9, 10 and 12 with a '
      + 'count of 0 — build the object from the data you were given.').toEqual({ 11: 1 });
  });
});
