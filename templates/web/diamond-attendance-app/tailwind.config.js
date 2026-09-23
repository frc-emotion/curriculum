/** @type {import('tailwindcss').Config} */
// Tailwind needs to know which files to scan for class names. If you add a new
// folder under src/, it is already covered by the pattern below.
module.exports = {
  content: ['./App.tsx', './src/**/*.{js,jsx,ts,tsx}'],
  presets: [require('nativewind/preset')],
  theme: {
    extend: {},
  },
  plugins: [],
};
