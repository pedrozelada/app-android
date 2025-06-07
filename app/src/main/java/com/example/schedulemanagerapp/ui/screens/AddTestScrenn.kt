// --- AddTestScreen.kt ---
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
import com.example.schedulemanagerapp.data.model.Test
import com.example.schedulemanagerapp.ui.components.Routes
import com.example.schedulemanagerapp.viewmodel.ScheduleViewModel
import java.time.LocalDate
import java.time.format.DateTimeParseException

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTestScreen(
    navController: NavController,
    viewModel: ScheduleViewModel = hiltViewModel()
) {
    val courses by viewModel.courses.collectAsState()

    var selectedCourse by remember { mutableStateOf<String?>(null) }
    var topic by remember { mutableStateOf(TextFieldValue()) }
    var date by remember { mutableStateOf(TextFieldValue()) }
    var place by remember { mutableStateOf(TextFieldValue()) }

    LaunchedEffect(Unit) {
        viewModel.loadCourses()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Add Test") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                try {
                    val parsedDate = LocalDate.parse(date.text)
                    selectedCourse?.let {
                        viewModel.addTest(Test(0, it, topic.text, parsedDate, place.text))
                        navController.popBackStack()
                    }
                } catch (e: DateTimeParseException) {
                    // Show error feedback in production
                }
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
            // Course dropdown
            ExposedDropdownMenuBox(
                expanded = selectedCourse == null,
                onExpandedChange = {}
            ) {
                Text(text = selectedCourse ?: "Select Course")
                DropdownMenu(
                    expanded = true,
                    onDismissRequest = {}
                ) {
                    courses.forEach { course ->
                        DropdownMenuItem(
                            text = { Text(course.name) },
                            onClick = { selectedCourse = course.code }
                        )
                    }
                }
            }
            OutlinedTextField(
                value = topic,
                onValueChange = { topic = it },
                label = { Text("Topic") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = date,
                onValueChange = { date = it },
                label = { Text("Date (yyyy-MM-dd)") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = place,
                onValueChange = { place = it },
                label = { Text("Location") },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

