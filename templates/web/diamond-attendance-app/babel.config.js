// Babel turns your JSX and TypeScript into something React Native can run.
// The nativewind preset is what makes `className` work on React Native views.
// You don't need to change this file.
module.exports = function babelConfig(api) {
  api.cache(true);
  return {
    presets: [
      ['babel-preset-expo', { jsxImportSource: 'nativewind' }],
      'nativewind/babel',
    ],
    plugins: ['react-native-worklets/plugin'],
  };
};
