/* ============================================================
 * Helpers shared by the rank checks.
 *
 * Part of the grader, not the assessment. You don't need to read it, and you
 * shouldn't edit it.
 * ============================================================ */
import * as api from '../src/api.js';
import * as grouping from '../src/groupBy.js';
import * as utils from '../src/studentUtils.js';
import { students as rawStudents } from '../src/data/students.js';

type AnyFunction = (...args: never[]) => unknown;

const MODULES: Record<string, Record<string, unknown>> = {
  'src/studentUtils.ts': utils as unknown as Record<string, unknown>,
  'src/groupBy.ts': grouping as unknown as Record<string, unknown>,
  'src/api.ts': api as unknown as Record<string, unknown>,
};

/** Finds one of your exported functions, or fails naming the step. */
export function fn(step: number, file: string, name: string): AnyFunction {
  const candidate = MODULES[file]?.[name];
  if (typeof candidate !== 'function') {
    throw new Error(
      `STEP ${step}: I couldn't find an exported function called \`${name}\` in ${file}. ` +
        `Check the spelling, and that the line starts with \`export\`.`,
    );
  }
  return candidate as AnyFunction;
}

export function deepFreeze<T>(value: T): T {
  if (value && typeof value === 'object' && !Object.isFrozen(value)) {
    Object.freeze(value);
    for (const key of Object.keys(value as object)) {
      deepFreeze((value as Record<string, unknown>)[key]);
    }
  }
  return value;
}

/** A fresh, deep-frozen copy of the roster. */
export function frozenStudents(): ReadonlyArray<Record<string, unknown>> {
  return deepFreeze(structuredClone(rawStudents)) as ReadonlyArray<Record<string, unknown>>;
}

export function plainStudents(): Array<Record<string, unknown>> {
  return structuredClone(rawStudents) as Array<Record<string, unknown>>;
}
