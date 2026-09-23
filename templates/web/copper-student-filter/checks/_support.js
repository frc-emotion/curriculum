// ============================================================
// Helpers shared by the rank checks.
//
// This is part of the grader, not the assessment. You don't need to read it,
// and you shouldn't edit it — if you change the grader, your reviewer will
// notice.
// ============================================================
import * as utils from '../src/studentUtils.js';
import { students as rawStudents } from '../src/students.js';

/** Recursively freezes an object so any attempt to change it throws. */
export function deepFreeze(value) {
  if (value && typeof value === 'object' && !Object.isFrozen(value)) {
    Object.freeze(value);
    for (const key of Object.keys(value)) {
      deepFreeze(value[key]);
    }
  }
  return value;
}

/**
 * A fresh, deep-frozen copy of the roster.
 *
 * Frozen on purpose: if your function tries to change the array it was handed,
 * it will throw here rather than quietly corrupting data somewhere else.
 */
export function frozenStudents() {
  return deepFreeze(structuredClone(rawStudents));
}

/** The roster, unfrozen, for building expectations from. */
export function plainStudents() {
  return structuredClone(rawStudents);
}

const MUTATION_HINTS = [
  'read only',
  'read-only',
  'not extensible',
  'object is not extensible',
  'cannot add',
  'cannot assign',
  'cannot delete',
  'sealed',
  'frozen',
];

/** Finds one of your exported functions, or fails naming the step. */
export function fn(step, name) {
  const candidate = utils[name];
  if (typeof candidate !== 'function') {
    throw new Error(
      `STEP ${step}: I couldn't find an exported function called \`${name}\` in ` +
        `src/studentUtils.js. Check the spelling, and that the line starts with \`export\`.`,
    );
  }
  return candidate;
}

/**
 * Calls one of your functions, turning a "you changed the frozen input" crash
 * into a message that says so.
 */
export function call(step, name, ...args) {
  const target = fn(step, name);
  try {
    return target(...args);
  } catch (error) {
    const message = String(error?.message ?? error).toLowerCase();
    if (error instanceof TypeError && MUTATION_HINTS.some((hint) => message.includes(hint))) {
      throw new Error(
        `STEP ${step}: \`${name}\` tried to change the array it was given. The checks freeze ` +
          `the input on purpose. Make a copy first — [...students] gives you one — and work ` +
          `on that.`,
      );
    }
    throw error;
  }
}
