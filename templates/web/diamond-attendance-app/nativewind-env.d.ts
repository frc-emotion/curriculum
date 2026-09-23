/// <reference types="nativewind/types" />
// This tells TypeScript that React Native components accept a `className` prop.
// Without it, every className you write would be a type error.
//
// The second declaration lets App.tsx import global.css for its side effect —
// Metro handles that file, TypeScript just needs to know it is allowed.
//
// You don't need to change this file.

declare module '*.css';
