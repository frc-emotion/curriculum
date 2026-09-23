import { describe, expect, it } from 'vitest';
import { call, frozenStudents } from './_support.js';

describe('STEP 5: getRegularAttendees', () => {
  it('keeps only the students who met the minimum', () => {
    const regulars = call(5, 'getRegularAttendees', frozenStudents(), 15);

    expect(Array.isArray(regulars), 'STEP 5: getRegularAttendees should return an array.').toBe(true);
    expect(regulars.length,
      'STEP 5: seven students attended 15 meetings or more. "At least" includes exactly 15.').toBe(7);
    expect(regulars.every((student) => student.meetingsAttended >= 15),
      'STEP 5: one of the students you returned attended fewer than 15 meetings.').toBe(true);
  });

  it('sorts from most meetings to fewest', () => {
    const counts = call(5, 'getRegularAttendees', frozenStudents(), 10)
      .map((student) => student.meetingsAttended);
    const sorted = [...counts].sort((a, b) => b - a);
    expect(counts,
      'STEP 5: the list should run from most meetings to fewest. Got: ' + counts.join(', ')).toEqual(sorted);
  });

  it('breaks ties alphabetically by name', () => {
    const names = call(5, 'getRegularAttendees', frozenStudents(), 15)
      .filter((student) => student.meetingsAttended === 15)
      .map((student) => student.name);

    expect(names,
      'STEP 5: Dara Silva, Fen Liu and Lena Voss all attended 15 meetings. When the counts tie, '
      + 'fall back to comparing names, so the order is the same every time you run it. Got: '
      + names.join(', ')).toEqual(['Dara Silva', 'Fen Liu', 'Lena Voss']);
  });

  it('gives an empty array when nobody qualifies', () => {
    expect(call(5, 'getRegularAttendees', frozenStudents(), 100),
      'STEP 5: nobody attended 100 meetings, so the answer is an empty array.').toEqual([]);
  });

  it('does not sort the roster it was given', () => {
    const roster = frozenStudents();
    call(5, 'getRegularAttendees', roster, 10);
    expect(roster.map((student) => student.id),
      'STEP 5: the roster came back in a different order, so `.sort()` was called on it directly. '
      + '`.sort()` changes the array in place — copy it first with [...students].')
      .toEqual([1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]);
  });
});
