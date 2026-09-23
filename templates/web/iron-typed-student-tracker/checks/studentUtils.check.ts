import { describe, expect, it } from 'vitest';
import { fn, frozenStudents } from './_support.js';

const FILE = 'src/studentUtils.ts';

function call(step: number, name: string, ...args: unknown[]): unknown {
  return (fn(step, FILE, name) as (...a: unknown[]) => unknown)(...args);
}

describe('STEP 2: your Copper functions, now typed', () => {
  it('getStudentNames returns every name in order', () => {
    const names = call(2, 'getStudentNames', frozenStudents()) as string[];
    expect(Array.isArray(names), 'STEP 2: getStudentNames should return an array.').toBe(true);
    expect(names.length, 'STEP 2: the roster has 14 students now, not 12.').toBe(14);
    expect(names[0], 'STEP 2: the first name should be Ada Nwosu.').toBe('Ada Nwosu');
    expect(names[13], 'STEP 2: the last name should be Noor Haddad.').toBe('Noor Haddad');
  });

  it('getStudentsBySubteam filters', () => {
    const software = call(2, 'getStudentsBySubteam', frozenStudents(), 'Software') as unknown[];
    expect(software.length, 'STEP 2: five students are on Software.').toBe(5);
    expect(call(2, 'getStudentsBySubteam', frozenStudents(), 'Robotics'),
      'STEP 2: nobody is on Robotics, so the answer is an empty array.').toEqual([]);
  });

  it('findStudentById returns one student or undefined', () => {
    const found = call(2, 'findStudentById', frozenStudents(), 3) as { name: string };
    expect(found?.name, 'STEP 2: id 3 is Chidi Park.').toBe('Chidi Park');
    expect(call(2, 'findStudentById', frozenStudents(), 99),
      'STEP 2: there is no student 99. This is the one whose return type has to admit it might '
      + 'not find anything — Student | undefined.').toBeUndefined();
  });

  it('countByGrade summarises', () => {
    expect(call(2, 'countByGrade', frozenStudents()),
      'STEP 2: the 14-student roster has 3 in grade 9, 4 in grade 10, 4 in grade 11 and 3 in '
      + 'grade 12.').toEqual({ 9: 3, 10: 4, 11: 4, 12: 3 });
  });

  it('getRegularAttendees filters, sorts and breaks ties', () => {
    const regulars = call(2, 'getRegularAttendees', frozenStudents(), 15) as Array<{
      name: string; meetingsAttended: number;
    }>;
    expect(regulars.length, 'STEP 2: eight students attended 15 meetings or more.').toBe(8);

    const counts = regulars.map((student) => student.meetingsAttended);
    expect(counts, 'STEP 2: the list should run from most meetings to fewest.')
      .toEqual([...counts].sort((a, b) => b - a));

    const tied = regulars.filter((student) => student.meetingsAttended === 15)
      .map((student) => student.name);
    expect(tied, 'STEP 2: students tied on 15 meetings should come out alphabetically.')
      .toEqual(['Dara Silva', 'Fen Liu', 'Lena Voss']);
  });

  it('getRegularAttendees does not sort the roster it was given', () => {
    const roster = frozenStudents();
    call(2, 'getRegularAttendees', roster, 10);
    expect((roster as Array<{ id: number }>).map((student) => student.id),
      'STEP 2: the roster came back reordered, so `.sort()` ran on it directly. Copy it first.')
      .toEqual([1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14]);
  });

  it('formatStudent formats', () => {
    const ada = frozenStudents()[0];
    expect(call(2, 'formatStudent', ada),
      'STEP 2: the format is `Name (Grade N, Subteam)`, same as Copper.')
      .toBe('Ada Nwosu (Grade 11, Software)');
  });

  it('getContactEmail copes with missing contacts', () => {
    const roster = frozenStudents();
    expect(call(2, 'getContactEmail', roster[0]), 'STEP 2: Ada has an email.')
      .toBe('ada@example.com');
    expect(call(2, 'getContactEmail', roster[4]),
      'STEP 2: Emre has no contact object at all. Expected "no email on file".')
      .toBe('no email on file');
    expect(call(2, 'getContactEmail', roster[6]),
      'STEP 2: Gia has a contact with only a phone number. Expected "no email on file".')
      .toBe('no email on file');
  });
});
