package com.example.schedulemanagerapp.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.schedulemanagerapp.data.model.*
import com.example.schedulemanagerapp.data.room.*
import kotlinx.coroutines.flow.first
import java.time.DayOfWeek
import java.time.LocalDate

class RoomScheduleRepository(
    private val db: AppDatabase
) : ScheduleRepository {

    override suspend fun getAllCourses(): List<Course> =
        db.courseDao().getAllCourses().first().map { Course(it.code, it.name, it.teacher) }

    override suspend fun getCourseByCode(code: String): Course? =
        db.courseDao().getCourseByCode(code)?.let { Course(it.code, it.name, it.teacher) }

    override suspend fun addCourse(course: Course) {
        db.courseDao().insert(CourseEntity(course.code, course.name, course.teacher))
    }

    override suspend fun getSchedulesByDay(day: DayOfWeek): List<ClassSchedule> =
        db.scheduleDao().getSchedulesByDay(day).first().map {
            ClassSchedule(it.courseCode, it.day, it.start, it.end)
        }

    override suspend fun addSchedule(schedule: ClassSchedule) {
        db.scheduleDao().insert(ClassScheduleEntity(schedule.courseCode, schedule.day, schedule.start, schedule.end))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getUpcomingTests(days: Int): List<Test> {
        val today = LocalDate.now()
        val cutoff = today.plusDays(days.toLong())
        return db.testDao().getUpcomingTests(today, cutoff).map {
            Test(it.id, it.courseCode, it.topic, it.date, it.place)
        }
    }

    override suspend fun addTest(test: Test) {
        db.testDao().insert(TestEntity(0, test.courseCode, test.topic, test.date, test.place))
    }

    override suspend fun getAssignmentsByCourse(code: String): List<Assignment> =
        db.assignmentDao().getAssignmentsByCourse(code).first().map {
            Assignment(it.id, it.courseCode, it.description, it.dueDate, it.completed)
        }

    override suspend fun addAssignment(assignment: Assignment) {
        db.assignmentDao().insert(
            AssignmentEntity(
                0,
                assignment.courseCode,
                assignment.description,
                assignment.dueDate,
                assignment.completed
            )
        )
    }
}
