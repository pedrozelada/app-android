package com.example.schedulemanagerapp.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.schedulemanagerapp.data.model.ClassSchedule
import com.example.schedulemanagerapp.viewmodel.ScheduleViewModel
import java.time.DayOfWeek
import java.time.LocalTime
import java.time.format.DateTimeParseException

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddScheduleScreen(
    navController: NavController,
    viewModel: ScheduleViewModel = hiltViewModel()
) {
    val courses by viewModel.courses.collectAsState()

    var selectedCourse by remember { mutableStateOf<String?>(null) }
    var selectedDay by remember { mutableStateOf<DayOfWeek?>(null) }
    var startTime by remember { mutableStateOf(TextFieldValue()) }
    var endTime by remember { mutableStateOf(TextFieldValue()) }

    LaunchedEffect(Unit) {
        viewModel.loadCourses()
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Add Class Schedule") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                try {
                    val start = LocalTime.parse(startTime.text)
                    val end = LocalTime.parse(endTime.text)
                    if (start >= end) return@FloatingActionButton
                    if (selectedCourse != null && selectedDay != null) {
                        viewModel.addSchedule(ClassSchedule(selectedCourse!!,
                            selectedDay!!.toString(), start.toString(), end.toString()
                        ))
                        navController.popBackStack()
                    }
                } catch (_: DateTimeParseException) {}
            }) {
                Text("Save")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Course Dropdown
            var expandedCourse by remember { mutableStateOf(false) }
            ExposedDropdownMenuBox(
                expanded = expandedCourse,
                onExpandedChange = { expandedCourse = it }
            ) {
                OutlinedTextField(
                    value = selectedCourse ?: "Select Course",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Course") },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                DropdownMenu(
                    expanded = expandedCourse,
                    onDismissRequest = { expandedCourse = false }
                ) {
                    courses.forEach { course ->
                        DropdownMenuItem(
                            text = { Text(course.name) },
                            onClick = {
                                selectedCourse = course.code
                                expandedCourse = false
                            }
                        )
                    }
                }
            }
            // Day Dropdown
            var expandedDay by remember { mutableStateOf(false) }
            ExposedDropdownMenuBox(
                expanded = expandedDay,
                onExpandedChange = { expandedDay = it }
            ) {
                OutlinedTextField(
                    value = selectedDay?.name ?: "Select Day",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Day") },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                DropdownMenu(
                    expanded = expandedDay,
                    onDismissRequest = { expandedDay = false }
                ) {
                    DayOfWeek.values().forEach { day ->
                        DropdownMenuItem(
                            text = { Text(day.name) },
                            onClick = {
                                selectedDay = day
                                expandedDay = false
                            }
                        )
                    }
                }
            }
            OutlinedTextField(
                value = startTime,
                onValueChange = { startTime = it },
                label = { Text("Start Time (HH:mm)") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = endTime,
                onValueChange = { endTime = it },
                label = { Text("End Time (HH:mm)") },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


