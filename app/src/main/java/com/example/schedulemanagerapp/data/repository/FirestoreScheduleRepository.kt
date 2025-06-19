package com.example.schedulemanagerapp.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.schedulemanagerapp.data.model.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.time.DayOfWeek
import java.time.LocalDate

class FirestoreScheduleRepository : ScheduleRepository {

    // Firestore instance
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    // Get current authenticated user ID, throws if not signed in
    private val uid: String
        get() = FirebaseAuth.getInstance().currentUser?.uid
            ?: throw Exception("User not authenticated")

    // Fetch all courses for the current user
    override suspend fun getAllCourses(): List<Course> {
        return db.collection("users").document(uid)
            .collection("courses")
            .get().await()
            .toObjects(Course::class.java)
    }

    // Fetch a specific course by its code
    override suspend fun getCourseByCode(code: String): Course? {
        return db.collection("users").document(uid)
            .collection("courses").document(code)
            .get().await()
            .toObject(Course::class.java)
    }

    // Add or update a course under the user's collection
    override suspend fun addCourse(course: Course) {
        db.collection("users").document(uid)
            .collection("courses").document(course.code)
            .set(course).await()
    }

    // Fetch schedules for a specific day
    override suspend fun getSchedulesByDay(day: DayOfWeek): List<ClassSchedule> {
        return db.collection("users").document(uid)
            .collection("schedules")
            .whereEqualTo("day", day.name)
            .get().await()
            .toObjects(ClassSchedule::class.java)
    }

    // Add a class schedule
    override suspend fun addSchedule(schedule: ClassSchedule) {
        db.collection("users").document(uid)
            .collection("schedules")
            .add(schedule).await()
    }

    // Fetch upcoming tests within a range of days
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getUpcomingTests(days: Int): List<Test> {
        val today = LocalDate.now()
        val to = today.plusDays(days.toLong())

        return db.collection("users").document(uid)
            .collection("tests")
            .get().await()
            .toObjects(Test::class.java)
            .filter {
                // Parse and filter dates within the range
                val testDate = LocalDate.parse(it.date)
                testDate in today..to
            }
            .sortedBy { it.date }
    }

    // Add a test to Firestore
    override suspend fun addTest(test: Test) {
        db.collection("users").document(uid)
            .collection("tests")
            .add(test).await()
    }

    // Fetch assignments for a specific course
    override suspend fun getAssignmentsByCourse(code: String): List<Assignment> {
        return db.collection("users").document(uid)
            .collection("assignments")
            .whereEqualTo("courseCode", code)
            .get().await()
            .toObjects(Assignment::class.java)
    }

    // Add an assignment
    override suspend fun addAssignment(assignment: Assignment) {
        db.collection("users").document(uid)
            .collection("assignments")
            .add(assignment).await()
    }
}
