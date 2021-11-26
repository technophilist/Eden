# Eden - A pet adoption app 🐶 [![Project Status: Suspended – Initial development has started, but there has not yet been a stable, usable release; work has been stopped for the time being but I intend on resuming work.](https://www.repostatus.org/badges/latest/suspended.svg)](https://www.repostatus.org/#suspended)

Eden is a pet adoption app that demonstrates the use of [Jetpack Compose](https://developer.android.com/jetpack/compose?gclid=EAIaIQobChMI15Hjt8u29AIVGpNmAh0-MwGYEAAYASAAEgLQe_D_BwE&gclsrc=aw.ds) with a typical Android app. <br>
To try out this sample app, you need to use [Android Studio Arctic Fox](https://developer.android.com/studio).
# Screenshots
<img src = "screenshots/dark-mode/onboarding-dark.png" height = "360" width = "180"> &nbsp; <img src = "screenshots/dark-mode/log-in-dark.png" height = "360" width = "180">&nbsp; <img src = "screenshots/dark-mode/adoption-screen-dark.png" height = "360" width = "180"> &nbsp; <img src = "screenshots/dark-mode/pet-detail-screen-dark.png" height = "360" width = "180"> &nbsp; <img src = "screenshots/dark-mode/notifications-screen-dark.png" height = "360" width = "180"> 
# Tech Stack
- Entirely written in [Kotlin](https://kotlinlang.org/).
- Manual dependency injection.
- [Jetpack Compose](https://developer.android.com/jetpack/compose) for the entire UI.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines/coroutines-guide.html) for
  threading.
- [Timber](https://github.com/JakeWharton/timber) for logging.
- [DataStore](https://developer.android.com/topic/libraries/architecture/datastore?gclid=EAIaIQobChMItJiD6eTG8QIVQTUrCh0OSAGpEAAYASAAEgJ5H_D_BwE&gclsrc=aw.ds)
  for storing user preferences.
- [Firebase Authentication](https://firebase.google.com/docs/auth) for user account creation and authentication.
- [Firebase cloud storage](https://firebase.google.com/products/storage?gclid=EAIaIQobChMI0Nvz9M629AIVSyQrCh2FAA0rEAAYASAAEgLryvD_BwE&gclsrc=aw.ds) for storing
images
-[Accompanist library](https://google.github.io/accompanist/) for compose insets and pager layouts.
-[Lottie](https://airbnb.io/lottie/#/README) for animations.
-[Coil-compose](https://coil-kt.github.io/coil/compose/) for image loading.
