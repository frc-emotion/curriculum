// ============================================================
// ESLint config. This is the tool that reads your code and complains about
// things that are legal JavaScript but a bad idea anyway.
//
//   npm run lint    just the linter
//   npm run check   the linter and the rank checks together
//
// You do not need to edit this file. The three rules that matter for step 9
// are marked below.
// ============================================================
import js from '@eslint/js';

export default [
  js.configs.recommended,
  {
    files: ['**/*.js', '**/*.mjs'],
    languageOptions: {
      ecmaVersion: 2024,
      sourceType: 'module',
      globals: {
        console: 'readonly',
        process: 'readonly',
      },
    },
    rules: {
      // --- STEP 9 lives here ---
      // `var` is the old way of declaring variables and it behaves surprisingly.
      // Use `const` by default, and `let` when the value really does change.
      'no-var': 'error',

      // `==` converts types before comparing, so "" == 0 is true and so is
      // null == undefined. `===` compares without the guesswork.
      eqeqeq: ['error', 'always'],

      // A variable nobody reads is either a leftover or a typo. Both are worth
      // knowing about.
      'no-unused-vars': 'error',

      // Prefer const when a variable is never reassigned.
      'prefer-const': 'error',
    },
  },
  {
    ignores: ['node_modules/**', 'coverage/**'],
  },
];
