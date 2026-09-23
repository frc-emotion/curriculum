import { describe, expect, it } from 'vitest';
import { call, frozenStudents } from './_support.js';

describe('STEP 3: findStudentById', () => {
  it('finds the one student with that id', () => {
    const student = call(3, 'findStudentById', frozenStudents(), 3);

    expect(student, 'STEP 3: id 3 is Chidi Park, so something should come back.').toBeDefined();
    expect(Array.isArray(student),
      'STEP 3: findStudentById should return ONE student, not an array containing one. That is the '
      + 'difference between `.find` and `.filter`.').toBe(false);
    expect(student.name, 'STEP 3: id 3 should be Chidi Park.').toBe('Chidi Park');
  });

  it('finds the first and the last one too', () => {
    expect(call(3, 'findStudentById', frozenStudents(), 1).name,
      'STEP 3: id 1 should be Ada Nwosu.').toBe('Ada Nwosu');
    expect(call(3, 'findStudentById', frozenStudents(), 12).name,
      'STEP 3: id 12 should be Lena Voss.').toBe('Lena Voss');
  });

  it('gives undefined when the id is not on the roster', () => {
    expect(call(3, 'findStudentById', frozenStudents(), 99),
      'STEP 3: there is no student 99, so findStudentById should give back undefined.').toBeUndefined();
  });

  it('gives undefined for an empty roster', () => {
    expect(call(3, 'findStudentById', [], 1),
      'STEP 3: an empty roster has nobody in it, so the answer is undefined.').toBeUndefined();
  });
});
