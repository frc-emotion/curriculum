import react from '@vitejs/plugin-react';
import { defineConfig } from 'vitest/config';

// Vite is the dev server and build tool. `npm run dev` starts it. The `test` block is
// Vitest, which shares the same config file.
// You don't need to change this file.
export default defineConfig({
  plugins: [react()],
  test: {
    environment: 'jsdom',
    globals: false,
    include: ['checks/**/*.check.tsx'],
    setupFiles: ['./checks/setup.ts'],
  },
});
