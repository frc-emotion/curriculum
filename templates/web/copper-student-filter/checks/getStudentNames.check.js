import { describe, expect, it } from 'vitest';
import { call, frozenStudents, plainStudents } from './_support.js';

describe('STEP 1: getStudentNames', () => {
  it('gives back every name, in order', () => {
    const expected = plainStudents().map((student) => student.name);
    const actual = call(1, 'getStudentNames', frozenStudents());

    expect(Array.isArray(actual), 'STEP 1: getStudentNames should return an array.').toBe(true);
    expect(actual, 'STEP 1: getStudentNames should return each student\'s name, in the same order '
      + 'they appear in the roster.').toEqual(expected);
  });

  it('gives back an empty array for an empty roster', () => {
    expect(call(1, 'getStudentNames', []),
      'STEP 1: getStudentNames([]) should be an empty array, not undefined.').toEqual([]);
  });

  it('leaves the roster alone', () => {
    const roster = frozenStudents();
    call(1, 'getStudentNames', roster);
    expect(roster.length,
      'STEP 1: getStudentNames changed the roster it was given. `.map` returns a new array; it '
      + 'should not need to touch the old one.').toBe(12);
  });
});
