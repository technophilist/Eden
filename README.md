# Eden - A pet adoption app 🐶 [![Project Status: Inactive – The project has reached a stable, usable state but is no longer being actively developed; support/maintenance will be provided as time allows.](https://www.repostatus.org/badges/latest/inactive.svg)](https://www.repostatus.org/#inactive) ![Build workflow](https://github.com/t3chkid/Eden/actions/workflows/build.yml/badge.svg)

Eden is a pet adoption app that demonstrates the use of [Jetpack Compose](https://developer.android.com/jetpack/compose?gclid=EAIaIQobChMI15Hjt8u29AIVGpNmAh0-MwGYEAAYASAAEgLQe_D_BwE&gclsrc=aw.ds) with a typical Android app. This is a sample app and will not actually allow you to adopt pets.<br>
To try out this sample app, you need to use [Android Studio Arctic Fox](https://developer.android.com/studio).

## Table of contents
- [Screenshots](#screenshots)
- [Tech stack](#tech-stack)
- [Theme](#theme)
- [Source code and architecture](#source-code-and-architecture)

## Screenshots
<img src = "screenshots/dark-mode/onboarding-dark.png" height = "360" width = "180"> &nbsp; <img src = "screenshots/dark-mode/log-in-dark.png" height = "360" width = "180">&nbsp; <img src = "screenshots/dark-mode/sign-up-dark.png" height = "360" width = "180"> <br>
<img src = "screenshots/dark-mode/adoption-screen-dark.png" height = "360" width = "180"> &nbsp; <img src = "screenshots/dark-mode/pet-detail-screen-dark.png" height = "360" width = "180"> &nbsp; <img src = "screenshots/dark-mode/notifications-screen-dark.png" height = "360" width = "180"> 

## Theme
The color palette and the typography used in this app is entirely based on the specification for [Bloom app](https://github.com/android/android-dev-challenge-compose/blob/assets/Bloom.zip), which was released by google for the [AndroidDevChallenge](https://developer.android.com/events/dev-challenge).

### Color Palette
<img src = "https://github.com/t3chkid/Eden/blob/main/screenshots/Color%20palette.png" height = "600"> 

### Font Family
<img src = "https://github.com/t3chkid/Eden/blob/main/screenshots/Font%20Family.png" height = "300"> 

## Tech Stack
- Entirely written in [Kotlin](https://kotlinlang.org/).
- Manual dependency injection.
- [Jetpack Compose](https://developer.android.com/jetpack/compose) for the entire UI.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines/coroutines-guide.html) for
  threading.
- [Timber](https://github.com/JakeWharton/timber) for logging.
- [Firebase Authentication](https://firebase.google.com/docs/auth) for user account creation and authentication.
- [Firebase cloud storage](https://firebase.google.com/products/storage?gclid=EAIaIQobChMI0Nvz9M629AIVSyQrCh2FAA0rEAAYASAAEgLryvD_BwE&gclsrc=aw.ds) for storing
images
- [Accompanist library](https://google.github.io/accompanist/) for compose insets and pager layouts.
- [Lottie](https://airbnb.io/lottie/#/README) for animations.
- [Coil-compose](https://coil-kt.github.io/coil/compose/) for image loading.
- [Github actions](https://github.com/features/actions) for CI pipeline.
## Source code and architecture
- [Architecture components](https://developer.android.com/topic/libraries/architecture/) such as Livedata and ViewModels are used.
- [MVVM](https://developer.android.com/jetpack/guide?gclid=EAIaIQobChMI-_GIsejG8QIVzNaWCh0NXQANEAAYASAAEgKZ2fD_BwE&gclsrc=aw.ds)
  architecture.
- Source code conforms to
  the [Kotlin coding conventions](https://kotlinlang.org/docs/coding-conventions.html).
- Dependency injection is done manually.
- [Material design color system](https://material.io/design/color/the-color-system.html#color-usage-and-palettes)
  specification is used for assigning colors to the UI components.
- Commit messages follow the [Conventional Commits specification](https://www.conventionalcommits.org/en/v1.0.0/).
