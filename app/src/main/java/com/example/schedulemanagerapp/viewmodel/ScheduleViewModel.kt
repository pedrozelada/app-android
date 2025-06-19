package com.example.schedulemanagerapp.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.schedulemanagerapp.data.model.Course
import com.example.schedulemanagerapp.data.model.Test
import com.example.schedulemanagerapp.data.model.Assignment
import com.example.schedulemanagerapp.data.model.ClassSchedule
import com.example.schedulemanagerapp.data.repository.ScheduleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val repository: ScheduleRepository
) : ViewModel() {

    private val _courses = MutableStateFlow<List<Course>>(emptyList())
    val courses: StateFlow<List<Course>> = _courses.asStateFlow()

    private val _tests = MutableStateFlow<List<Test>>(emptyList())
    val tests: StateFlow<List<Test>> = _tests.asStateFlow()

    private val _assignments = MutableStateFlow<List<Assignment>>(emptyList())
    val assignments: StateFlow<List<Assignment>> = _assignments.asStateFlow()

    private val _schedules = MutableStateFlow<List<ClassSchedule>>(emptyList())
    val schedules: StateFlow<List<ClassSchedule>> = _schedules.asStateFlow()

    fun loadCourses() {
        viewModelScope.launch {
            _courses.value = repository.getAllCourses()
        }
    }

    fun loadUpcomingTests(days: Int = 7) {
        viewModelScope.launch {
            _tests.value = repository.getUpcomingTests(days)
        }
    }

    fun loadAssignmentsForCourse(courseCode: String) {
        viewModelScope.launch {
            _assignments.value = repository.getAssignmentsByCourse(courseCode)
        }
    }

    fun loadSchedulesByDay(day: DayOfWeek) {
        viewModelScope.launch {
            _schedules.value = repository.getSchedulesByDay(day)
        }
    }

    fun addCourse(course: Course) {
        viewModelScope.launch {
            repository.addCourse(course)
            loadCourses()
        }
    }

    fun addTest(test: Test) {
        viewModelScope.launch {
            repository.addTest(test)
            loadUpcomingTests()
        }
    }

    fun addAssignment(assignment: Assignment) {
        viewModelScope.launch {
            repository.addAssignment(assignment)
            loadAssignmentsForCourse(assignment.courseCode)
        }
    }

    fun addSchedule(schedule: ClassSchedule) {
        viewModelScope.launch {
            repository.addSchedule(schedule)
            loadSchedulesByDay(DayOfWeek.valueOf(schedule.day))
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun loadAssignmentsForToday() {
        val today = LocalDate.now()
        viewModelScope.launch {
            _assignments.value = courses.value
                .flatMap { repository.getAssignmentsByCourse(it.code) }
                .filter {
                    try {
                        LocalDate.parse(it.dueDate) == today
                    } catch (e: Exception) {
                        false
                    }
                }
        }
    }
}
