package com.example.schedulemanagerapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.schedulemanagerapp.data.model.Course
import com.example.schedulemanagerapp.ui.components.Routes
import com.example.schedulemanagerapp.viewmodel.ScheduleViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCourseScreen(
    navController: NavController,
    viewModel: ScheduleViewModel = hiltViewModel()
) {
    var code by remember { mutableStateOf(TextFieldValue()) }
    var name by remember { mutableStateOf(TextFieldValue()) }
    var teacher by remember { mutableStateOf(TextFieldValue()) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Add Course") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if (code.text.isNotBlank() && name.text.isNotBlank()) {
                    viewModel.addCourse(Course(code.text, name.text, teacher.text))
                    navController.popBackStack()
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
            OutlinedTextField(
                value = code,
                onValueChange = { code = it },
                label = { Text("Course Code") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Course Name") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = teacher,
                onValueChange = { teacher = it },
                label = { Text("Instructor") },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
