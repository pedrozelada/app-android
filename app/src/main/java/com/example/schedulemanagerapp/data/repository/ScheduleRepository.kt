package com.example.schedulemanagerapp.data.repository


import com.example.schedulemanagerapp.data.model.Assignment
import com.example.schedulemanagerapp.data.model.ClassSchedule
import com.example.schedulemanagerapp.data.model.Course
import com.example.schedulemanagerapp.data.model.Test
import java.time.DayOfWeek

interface ScheduleRepository {
    suspend fun getAllCourses(): List<Course>
    suspend fun getCourseByCode(code: String): Course?
    suspend fun addCourse(course: Course)

    suspend fun getSchedulesByDay(day: DayOfWeek): List<ClassSchedule>
    suspend fun addSchedule(schedule: ClassSchedule)

    suspend fun getUpcomingTests(days: Int = 7): List<Test>
    suspend fun addTest(test: Test)

    suspend fun getAssignmentsByCourse(code: String): List<Assignment>
    suspend fun addAssignment(assignment: Assignment)
}
