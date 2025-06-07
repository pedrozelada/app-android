package com.example.schedulemanagerapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.schedulemanagerapp.viewmodel.ScheduleViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssignmentListScreen(
    courseCode: String,
    viewModel: ScheduleViewModel = hiltViewModel()
) {
    val assignments by viewModel.assignments.collectAsState()

    LaunchedEffect(courseCode) {
        viewModel.loadAssignmentsForCourse(courseCode)
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Assignments for $courseCode") }) }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            LazyColumn {
                items(assignments) { assignment ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        colors = if (assignment.completed) CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                        else CardDefaults.cardColors()
                    ) {
                        Column(modifier = Modifier
                            .clickable { /* future: toggle complete */ }
                            .padding(16.dp)) {
                            Text(assignment.description, style = MaterialTheme.typography.titleMedium)
                            Text("Due: ${assignment.dueDate}", style = MaterialTheme.typography.bodySmall)
                            if (assignment.completed) Text("✓ Completed", color = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
            }
        }
    }
}
