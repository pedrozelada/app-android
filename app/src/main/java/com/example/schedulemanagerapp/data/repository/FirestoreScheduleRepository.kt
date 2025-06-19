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

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val uid: String get() = FirebaseAuth.getInstance().currentUser?.uid ?: throw Exception("User not authenticated")

    override suspend fun getAllCourses(): List<Course> {
        return db.collection("users").document(uid)
            .collection("courses")
            .get().await()
            .toObjects(Course::class.java)
    }

    override suspend fun getCourseByCode(code: String): Course? {
        return db.collection("users").document(uid)
            .collection("courses").document(code)
            .get().await().toObject(Course::class.java)
    }

    override suspend fun addCourse(course: Course) {
        db.collection("users").document(uid)
            .collection("courses").document(course.code)
            .set(course).await()
    }

    override suspend fun getSchedulesByDay(day: DayOfWeek): List<ClassSchedule> {
        return db.collection("users").document(uid)
            .collection("schedules")
            .whereEqualTo("day", day.name)
            .get().await()
            .toObjects(ClassSchedule::class.java)
    }

    override suspend fun addSchedule(schedule: ClassSchedule) {
        db.collection("users").document(uid)
            .collection("schedules")
            .add(schedule).await()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getUpcomingTests(days: Int): List<Test> {
        val today = LocalDate.now()
        val to = today.plusDays(days.toLong())

        return db.collection("users").document(uid)
            .collection("tests")
            .get().await()
            .toObjects(Test::class.java)
            .filter {
                val testDate = LocalDate.parse(it.date)
                testDate in today..to
            }


            .sortedBy { it.date }
    }

    override suspend fun addTest(test: Test) {
        db.collection("users").document(uid)
            .collection("tests")
            .add(test).await()
    }

    override suspend fun getAssignmentsByCourse(code: String): List<Assignment> {
        return db.collection("users").document(uid)
            .collection("assignments")
            .whereEqualTo("courseCode", code)
            .get().await()
            .toObjects(Assignment::class.java)
    }

    override suspend fun addAssignment(assignment: Assignment) {
        db.collection("users").document(uid)
            .collection("assignments")
            .add(assignment).await()
    }
}
