package com.example.schedulemanagerapp.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.schedulemanagerapp.viewmodel.ScheduleViewModel
import java.time.DayOfWeek
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScheduleOverviewScreen(
    viewModel: ScheduleViewModel = hiltViewModel()
) {
    val today = LocalDate.now()
    val dayOfWeek = today.dayOfWeek

    val schedules by viewModel.schedules.collectAsState()
    val tests by viewModel.tests.collectAsState()
    val assignments by viewModel.assignments.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadSchedulesByDay(dayOfWeek)
        viewModel.loadUpcomingTests(1)
        viewModel.loadAssignmentsForToday()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Today Overview: $today") })
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding).padding(16.dp)) {
            item { Text("Classes", style = MaterialTheme.typography.titleMedium) }
            if (schedules.isEmpty()) item { Text("No classes today.") }
            else items(schedules) { s ->
                Text("${s.courseCode}: ${s.start} - ${s.end}", style = MaterialTheme.typography.bodyMedium)
            }

            item { Spacer(Modifier.height(16.dp)) }
            item { Text("Tests", style = MaterialTheme.typography.titleMedium) }
            if (tests.isEmpty()) item { Text("No tests today.") }
            else items(tests.filter { try { LocalDate.parse(it.date) == today } catch (e: Exception) { false }
            }) { t ->
                Text("${t.courseCode} - ${t.topic} (${t.place})", style = MaterialTheme.typography.bodyMedium)
            }

            item { Spacer(Modifier.height(16.dp)) }
            item { Text("Assignments", style = MaterialTheme.typography.titleMedium) }
            if (assignments.isEmpty()) item { Text("No due assignments today.") }
            else items(assignments.filter {
                try { LocalDate.parse(it.dueDate) == today } catch (e: Exception) { false }
            }) { a ->
                Text("${a.courseCode} - ${a.description}", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
