import { describe, expect, it } from 'vitest';
import { fn, frozenStudents } from './_support.js';

const FILE = 'src/groupBy.ts';

function groupBy(items: unknown[], getKey: (item: never) => string): Record<string, unknown[]> {
  return (fn(3, FILE, 'groupBy') as (a: unknown, b: unknown) => Record<string, unknown[]>)(
    items,
    getKey,
  );
}

describe('STEP 3: groupBy', () => {
  it('groups students by subteam', () => {
    const grouped = groupBy(
      frozenStudents() as unknown[],
      (student: never) => (student as { subteam: string }).subteam,
    );

    expect(Object.keys(grouped).sort(),
      'STEP 3: grouping by subteam should give one key per subteam that appears.')
      .toEqual(['Electrical', 'Mechanical', 'Outreach', 'Software']);
    expect(grouped.Software?.length, 'STEP 3: five students are on Software.').toBe(5);
    expect(grouped.Electrical?.length, 'STEP 3: three students are on Electrical.').toBe(3);
  });

  it('keeps items in their original order inside each group', () => {
    const grouped = groupBy(
      frozenStudents() as unknown[],
      (student: never) => (student as { subteam: string }).subteam,
    );
    const softwareIds = (grouped.Software as Array<{ id: number }>).map((student) => student.id);
    expect(softwareIds,
      'STEP 3: within a group, keep the order the items came in. Push as you go rather than '
      + 'sorting.').toEqual([1, 3, 7, 9, 12]);
  });

  it('works on any key, not just subteam', () => {
    const grouped = groupBy(
      frozenStudents() as unknown[],
      (student: never) => String((student as { grade: number }).grade),
    );
    expect(Object.keys(grouped).sort(), 'STEP 3: grouping by grade should give four keys.')
      .toEqual(['10', '11', '12', '9']);
  });

  it('works on things that are not students at all', () => {
    const words = ['apple', 'avocado', 'banana', 'blueberry', 'cherry'];
    const grouped = groupBy(words, (word: never) => (word as string)[0] as string);
    expect(grouped,
      'STEP 3: a generic function works on a list of anything. If this one only accepts students, '
      + 'the <T> is not doing its job.')
      .toEqual({ a: ['apple', 'avocado'], b: ['banana', 'blueberry'], c: ['cherry'] });
  });

  it('gives an empty object for an empty list', () => {
    expect(groupBy([], () => 'anything'),
      'STEP 3: nothing to group means {}.').toEqual({});
  });
});
