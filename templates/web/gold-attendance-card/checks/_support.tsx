/* ============================================================
 * Helpers shared by the rank checks.
 *
 * Part of the grader, not the assessment. Don't edit.
 * ============================================================ */
import type { ComponentType } from 'react';
import * as CardModule from '../src/components/AttendanceCard.tsx';
import * as ListModule from '../src/components/AttendanceList.tsx';

type AnyProps = Record<string, unknown>;

/**
 * Finds a component that may be exported as the default OR by name, so both
 * styles are accepted.
 */
function pickComponent(
  step: number,
  file: string,
  name: string,
  module: Record<string, unknown>,
): ComponentType<AnyProps> {
  const candidate = module.default ?? module[name];
  if (typeof candidate !== 'function') {
    throw new Error(
      `STEP ${step}: I couldn't find a component in ${file}. Export it either as the default ` +
        `export or as a named export called \`${name}\` — both are fine.`,
    );
  }
  return candidate as ComponentType<AnyProps>;
}

export function attendanceCard(step = 1): ComponentType<AnyProps> {
  return pickComponent(
    step,
    'src/components/AttendanceCard.tsx',
    'AttendanceCard',
    CardModule as unknown as Record<string, unknown>,
  );
}

export function attendanceList(step = 3): ComponentType<AnyProps> {
  return pickComponent(
    step,
    'src/components/AttendanceList.tsx',
    'AttendanceList',
    ListModule as unknown as Record<string, unknown>,
  );
}
