import react from '@vitejs/plugin-react';
import { defineConfig } from 'vite';

// Vite is the dev server and build tool. `npm run dev` starts it.
// You don't need to change this file.
export default defineConfig({
  plugins: [react()],
});
