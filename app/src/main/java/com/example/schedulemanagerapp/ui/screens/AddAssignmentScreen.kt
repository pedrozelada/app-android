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
import com.example.schedulemanagerapp.data.model.Assignment
import com.example.schedulemanagerapp.viewmodel.ScheduleViewModel
import java.time.LocalDate
import java.time.format.DateTimeParseException

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAssignmentScreen(
    courseCode: String,
    navController: NavController,
    viewModel: ScheduleViewModel = hiltViewModel()
) {
    var description by remember { mutableStateOf(TextFieldValue()) }
    var dueDate by remember { mutableStateOf(TextFieldValue()) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Add Assignment") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                try {
                    val date = LocalDate.parse(dueDate.text)
                    viewModel.addAssignment(
                        Assignment(0, courseCode, description.text, date.toString(), completed = false)
                    )
                    navController.popBackStack()
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
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = dueDate,
                onValueChange = { dueDate = it },
                label = { Text("Due Date (yyyy-MM-dd)") },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
