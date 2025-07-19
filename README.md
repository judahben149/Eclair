[![Android](https://img.shields.io/github/actions/workflow/status/judahben149/Eclair/build.yml?label=Android&branch=main&color=green)](https://github.com/judahben149/Eclair/actions/workflows/build.yml)
[![Desktop](https://img.shields.io/github/actions/workflow/status/judahben149/Eclair/build.yml?label=Desktop&branch=main&color=orange)](https://github.com/judahben149/Eclair/actions/workflows/build.yml)
[![iOS](https://img.shields.io/github/actions/workflow/status/judahben149/Eclair/build.yml?label=iOS&branch=main&color=blue)](https://github.com/judahben149/Eclair/actions/workflows/build.yml)


This is a Kotlin Multiplatform project targeting Android, iOS, Desktop.

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform,
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.


Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…
