# GitHub User Search
A simple Github User Search Application.

[![N|Solid](https://i.postimg.cc/026DpBd9/screenshot-1652809903737.png)]

## Features
* Fetch User Based keyword entered by user on Search View
* See User Details (followers, Github Repo etc)

## Architecture
* Built with Modern Android Development practices
* Multi Module Application demonstrates SOLID principles and Clean Architecture
* Utilized remote, data, domain and presentation Layer
* Includes unit and integration tests of each module

## Tech Stack
- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - For asynchronous and more..
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Data objects that notify views when the underlying database changes.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes.
  - [ViewBinding](https://developer.android.com/topic/libraries/view-binding) - Generates a binding class for each XML layout file present in that module and allows you to more easily write code that interacts with views.
- [Dependency Injection](https://developer.android.com/training/dependency-injection)
  - [Hilt](https://dagger.dev/hilt) - Easier way to incorporate Dagger DI into Android apps.
- [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android and Java.
- [Paging3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) - For loading pagination data in more robust way
- [MockK](https://mockk.io) - For Mocking and Unit Testing
- [Espresso](https://developer.android.com/training/testing/espresso) -For UI Testing
- [CMake](https://developer.android.com/ndk/guides/cmake) -For Storing Credentails to prevent expose of Keys when doing reverse engineering