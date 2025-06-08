package com.example.schedulemanagerapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.schedulemanagerapp.viewmodel.ScheduleViewModel
import com.example.schedulemanagerapp.ui.components.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseListScreen(
    navController: NavController,
    viewModel: ScheduleViewModel = hiltViewModel()
) {
    val courses by viewModel.courses.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadCourses()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Courses") })
        },
        floatingActionButton = {
            Row(modifier = Modifier.padding(16.dp)) {
                FloatingActionButton(
                    onClick = { navController.navigate(Routes.ADD_COURSE) },
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Course")
                }
                FloatingActionButton(
                    onClick = { navController.navigate(Routes.ADD_TEST) }
                ) {
                    Text("Test")
                }
            }
        }
    ) { padding ->
        Column(modifier = Modifier
            .padding(padding)
            .padding(16.dp)
            .fillMaxSize()) {
            LazyColumn {
                items(courses) { course ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = course.code, style = MaterialTheme.typography.titleMedium)
                            Text(text = course.name, style = MaterialTheme.typography.bodyLarge)
                            Text(text = "Instructor: ${course.teacher}", style = MaterialTheme.typography.bodySmall)
                            Row(modifier = Modifier.padding(top = 8.dp)) {
                                TextButton(onClick = {
                                    navController.navigate("assignment_list/${course.code}")
                                }) { Text("Assignments") }
                                Spacer(modifier = Modifier.width(8.dp))
                                TextButton(onClick = {
                                    navController.navigate("add_assignment/${course.code}")
                                }) { Text("+Assignment") }
                            }
                        }
                    }
                }
            }
        }
    }
}
