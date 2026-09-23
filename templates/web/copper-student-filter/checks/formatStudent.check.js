import { describe, expect, it } from 'vitest';
import { call, frozenStudents } from './_support.js';

describe('STEP 6: formatStudent', () => {
  it('formats a student exactly as specified', () => {
    const ada = frozenStudents()[0];
    expect(call(6, 'formatStudent', ada),
      'STEP 6: the format is `Name (Grade N, Subteam)` — mind the brackets and the comma.')
      .toBe('Ada Nwosu (Grade 11, Software)');
  });

  it('works for any student', () => {
    const roster = frozenStudents();
    expect(call(6, 'formatStudent', roster[4]),
      'STEP 6: Emre Kaya is in grade 9 on Outreach.').toBe('Emre Kaya (Grade 9, Outreach)');
    expect(call(6, 'formatStudent', roster[7]),
      'STEP 6: Hari Das is in grade 12 on Electrical.').toBe('Hari Das (Grade 12, Electrical)');
  });

  it('reads the fields it was given, not the roster', () => {
    const invented = { id: 99, name: 'Sam Reed', grade: 10, subteam: 'Mechanical', meetingsAttended: 3 };
    expect(call(6, 'formatStudent', invented),
      'STEP 6: formatStudent should work on any student object handed to it.')
      .toBe('Sam Reed (Grade 10, Mechanical)');
  });
});
