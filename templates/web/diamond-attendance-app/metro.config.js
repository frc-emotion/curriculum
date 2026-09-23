// Metro is React Native's bundler. This wires NativeWind's CSS into it.
// You don't need to change this file.
const { getDefaultConfig } = require('expo/metro-config');
const { withNativeWind } = require('nativewind/metro');

const config = getDefaultConfig(__dirname);

module.exports = withNativeWind(config, { input: './global.css' });
