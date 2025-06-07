package com.example.schedulemanagerapp.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.schedulemanagerapp.data.room.*
import com.example.schedulemanagerapp.data.model.*
import java.time.DayOfWeek
import java.time.LocalDate

class InMemoryScheduleRepository : ScheduleRepository {

    private val courses = mutableListOf<Course>()
    private val schedules = mutableListOf<ClassSchedule>()
    private val tests = mutableListOf<Test>()
    private val assignments = mutableListOf<Assignment>()

    override suspend fun getAllCourses(): List<Course> {
        return courses.toList()
    }

    override suspend fun getCourseByCode(code: String): Course? {
        return courses.find { it.code == code }
    }

    override suspend fun addCourse(course: Course) {
        if (getCourseByCode(course.code) == null) {
            courses.add(course)
        } else {
            // Optionally update the course if it exists
        }
    }

    override suspend fun getSchedulesByDay(day: DayOfWeek): List<ClassSchedule> {
        return schedules.filter { it.day == day }
            .sortedBy { it.start }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun addSchedule(schedule: ClassSchedule) {
        val overlapping = schedules.any {
            it.day == schedule.day && it.courseCode == schedule.courseCode &&
                    it.start < schedule.end && schedule.start < it.end
        }

        if (!overlapping) {
            schedules.add(schedule)
        } else {
            throw IllegalArgumentException("Schedule conflict detected.")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getUpcomingTests(days: Int): List<Test> {
        val today = LocalDate.now()
        val cutoff = today.plusDays(days.toLong())
        return tests.filter { it.date in today..cutoff }
            .sortedBy { it.date }
    }

    override suspend fun addTest(test: Test) {
        tests.add(test)
    }

    override suspend fun getAssignmentsByCourse(code: String): List<Assignment> {
        return assignments.filter { it.courseCode == code }
            .sortedBy { it.dueDate }
    }

    override suspend fun addAssignment(assignment: Assignment) {
        assignments.add(assignment)
    }
}
