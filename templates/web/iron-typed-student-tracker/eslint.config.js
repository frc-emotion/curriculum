// ============================================================
// ESLint config for a TypeScript project.
//
//   npm run lint    just the linter
//   npm run check   the linter, the type checker and the rank checks
//
// You do not need to edit this file. The two rules that matter for step 9 are
// marked below.
// ============================================================
import js from '@eslint/js';
import tseslint from 'typescript-eslint';

export default tseslint.config(
  js.configs.recommended,
  ...tseslint.configs.recommended,
  {
    files: ['**/*.ts'],
    rules: {
      // --- STEP 9 lives here ---
      // `any` switches TypeScript off for that value. If you need an escape
      // hatch, `unknown` is the honest one: it makes you check before you use.
      '@typescript-eslint/no-explicit-any': 'error',

      // Silencing an error does not fix it.
      '@typescript-eslint/ban-ts-comment': 'error',

      'no-var': 'error',
      eqeqeq: ['error', 'always'],
      'prefer-const': 'error',
    },
  },
  {
    ignores: ['node_modules/**', 'coverage/**'],
  },
);
