package com.example.schedulemanagerapp.ui.screens

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailyScheduleScreen(
    selectedDay: DayOfWeek,
    viewModel: ScheduleViewModel = hiltViewModel()
) {
    val schedules by viewModel.schedules.collectAsState()

    LaunchedEffect(selectedDay) {
        viewModel.loadSchedulesByDay(selectedDay)
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Schedule: $selectedDay") }) }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            if (schedules.isEmpty()) {
                Text("No classes scheduled.")
            } else {
                LazyColumn {
                    items(schedules) { s ->
                        Card(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
                            Column(Modifier.padding(12.dp)) {
                                Text("${s.courseCode} ${s.start} - ${s.end}", style = MaterialTheme.typography.titleSmall)
                            }
                        }
                    }
                }
            }
        }
    }
}
