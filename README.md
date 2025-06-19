# ScheduleManagerApp

ScheduleManagerApp is a productivity-focused Android app built using modern Kotlin and Jetpack Compose. It helps users manage academic schedules by adding and organizing courses, assignments, exams, and weekly timetables efficiently.

## ✨ Purpose
As a software engineer, I’m always looking to expand my skill set and build real-world applications using modern tools and architectures. This project deepened my understanding of Kotlin and Jetpack Compose while preparing me to work with Firebase technologies like Firestore and Authentication.

## 🧠 Features
- Add and manage **courses**, **tests**, **assignments**, and **class schedules**
- Automatically prevents time conflicts when scheduling
- Persistent cloud storage using **Firebase Firestore**
- **User login and registration** with Firebase Authentication
- Offline access with Room (optional fallback architecture)
- MVVM architecture with ViewModel and StateFlow
- Clean UI powered by **Jetpack Compose** and Material3

[Software Demo Video](https://youtu.be/at9BLzUtv1Q)

# Cloud Database

We are using **Firebase Firestore** as the cloud database for `ScheduleManagerApp`. Firestore is a scalable NoSQL database provided by Google that supports real-time syncing and offline support, making it ideal for mobile applications.

## Firestore Structure
Data is organized per user using their Firebase Authentication UID, allowing each authenticated user to have a separate dataset:

```
users/{uid}/courses        // Stores course info (code, name, teacher)
users/{uid}/assignments    // Stores assignments with dueDate, courseCode, etc.
users/{uid}/tests          // Stores tests with date, topic, location
users/{uid}/schedules      // Stores daily class schedules (start, end, day)
```

Each subcollection (e.g., `courses`, `assignments`) contains documents mapped to Kotlin data models. All models are serialized/deserialized using Firestore’s auto-mapping with default values and string-safe dates.

This structure ensures user data is isolated, easy to query, and prepared for multi-device syncing or collaborative features in the future.


# Development Environment
- **Android Studio Hedgehog**
- **Kotlin** programming language
- **Jetpack Compose** for building UI
- **Room** for local DB (legacy or offline usage)
- **Firebase Firestore** for cloud storage
- **Firebase Authentication** for secure user login
- **ViewModel** & **StateFlow** for UI state handling


# Useful Websites
* [Kotlin Official Documentation](https://kotlinlang.org/docs/home.html)
* [Jetpack Compose Documentation](https://developer.android.com/jetpack/compose/documentation)
* [Android Developers - ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
* [Firebase Firestore Docs](https://firebase.google.com/docs/firestore)


# Future Work
* Add push notifications for upcoming tasks/tests
* Allow theme switching (dark/light mode)
* Export/Import schedules (e.g., to PDF or calendar)
