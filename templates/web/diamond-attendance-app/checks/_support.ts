/* ============================================================
 * Helpers shared by the rank checks.
 *
 * Jest's expect() has no message argument, so the checks throw their own errors
 * instead. That is what keeps every failure starting with a step number.
 *
 * Part of the grader. Don't edit.
 * ============================================================ */

export function assert(condition: unknown, message: string): asserts condition {
  if (!condition) {
    throw new Error(message);
  }
}

function show(value: unknown): string {
  if (typeof value === 'string') {
    return JSON.stringify(value);
  }
  try {
    return JSON.stringify(value) ?? String(value);
  } catch {
    return String(value);
  }
}

export function assertEqual(actual: unknown, expected: unknown, message: string): void {
  if (!Object.is(actual, expected)) {
    throw new Error(`${message} (got ${show(actual)}, expected ${show(expected)})`);
  }
}

export function assertDeepEqual(actual: unknown, expected: unknown, message: string): void {
  if (JSON.stringify(actual) !== JSON.stringify(expected)) {
    throw new Error(`${message} (got ${show(actual)}, expected ${show(expected)})`);
  }
}

type Module = Record<string, unknown>;

/** Finds a component exported as the default OR by name. */
export function component(step: number, file: string, name: string, module: Module): unknown {
  const candidate = module.default ?? module[name];
  assert(
    typeof candidate === 'function',
    `STEP ${step}: I couldn't find a component in ${file}. Export it either as the default ` +
      `export or as a named export called \`${name}\` — both are fine.`,
  );
  return candidate;
}

/** Finds an exported function, or fails naming the step. */
export function fn(step: number, file: string, name: string, module: Module): CallableFunction {
  const candidate = module[name];
  assert(
    typeof candidate === 'function',
    `STEP ${step}: I couldn't find an exported function called \`${name}\` in ${file}.`,
  );
  return candidate as CallableFunction;
}
