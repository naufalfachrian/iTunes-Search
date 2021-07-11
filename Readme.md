# iTunes Search Android App

This is an Android app that make use of iTunes Search API to search and
preview songs available on Apple's iTunes platform.

## Supported Devices

This app should run on Android devices using Android 7.0 (Nougat) and newer.
Active Internet connection is required.

## Supported Features

This app features :
- Search music available on iTunes platform
- Play preview music directly on your phone from iTunes server
- Display song title, artists, album name, and cover art
- Play and pause playback

## Requirement to Build

To build this app, you need :
- Android Studio 4.2 and more recent
- Android SDK version 30
- Active Internet connection is required for the first time build

## Instruction to Build and Deploy

It is recommended to use Android Studio to build and deploy
this app.

But if you would like to build app using command line, you can use gradlew.
Make sure `java` is available on PATH

To build APK, run
`gradlew assemble`

To deploy app to connected device(s), run
`gradlew install`
