/* Grader setup: extra `expect` matchers, and unmounting between checks.
   Part of the grader. Don't edit. */
import '@testing-library/jest-dom/vitest';
import { cleanup } from '@testing-library/react';
import { afterEach } from 'vitest';

// This config doesn't use Vitest globals, so React Testing Library's automatic
// cleanup never registers itself. Without this, each check would still see the
// previous check's DOM.
afterEach(cleanup);
