// Tells Vitest where the rank checks live. You don't need to change this.
import { defineConfig } from 'vitest/config';

export default defineConfig({
  test: {
    include: ['checks/**/*.check.ts'],
  },
});
