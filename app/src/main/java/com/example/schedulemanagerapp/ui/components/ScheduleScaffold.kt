package com.example.schedulemanagerapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.schedulemanagerapp.ui.components.Routes
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleScaffold(
    navController: NavController,
    content: @Composable () -> Unit

) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val userEmail = FirebaseAuth.getInstance().currentUser?.email ?: ""

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Schedule Manager", style = MaterialTheme.typography.titleLarge)
                    if (userEmail.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(userEmail, style = MaterialTheme.typography.bodySmall)
                    }
                }
                NavigationDrawerItem(
                    label = { Text("Home") },
                    selected = false,
                    onClick = {
                        navController.navigate(Routes.COURSE_LIST)
                    },
                    icon = { Icon(Icons.Default.Home, contentDescription = null) }
                )
                NavigationDrawerItem(
                    label = { Text("Add Course") },
                    selected = false,
                    onClick = { navController.navigate(Routes.ADD_COURSE) },
                    icon = { Icon(Icons.Default.Add, contentDescription = null) }
                )
                NavigationDrawerItem(
                    label = { Text("Add Test") },
                    selected = false,
                    onClick = { navController.navigate(Routes.ADD_TEST) },
                    icon = { Icon(Icons.Default.Event, contentDescription = null) }
                )
                NavigationDrawerItem(
                    label = { Text("Add Schedule") },
                    selected = false,
                    onClick = { navController.navigate(Routes.ADD_SCHEDULE) },
                    icon = { Icon(Icons.Default.Schedule, contentDescription = null) }
                )
                NavigationDrawerItem(
                    label = { Text("Schedule Today") },
                    selected = false,
                    onClick = { navController.navigate(Routes.OVERVIEW) },
                    icon = { Icon(Icons.Default.Today, contentDescription = null) }
                )
                NavigationDrawerItem(
                    label = { Text("Daily Schedule") },
                    selected = false,
                    onClick = { navController.navigate("daily_schedule/MONDAY") },
                    icon = { Icon(Icons.Default.DateRange, contentDescription = null) }
                )
                Spacer(modifier = Modifier.weight(1f))
                Divider()
                NavigationDrawerItem(
                    label = { Text("Logout") },
                    selected = false,
                    onClick = {
                        FirebaseAuth.getInstance().signOut()
                        navController.navigate(Routes.LOGIN) {
                            popUpTo(0) { inclusive = true }
                        }
                    },
                    icon = { Icon(Icons.Default.ExitToApp, contentDescription = null) }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Schedule App") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch { drawerState.open() }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        ) { padding ->
            Surface(modifier = Modifier.padding(padding)) {
                content()
            }
        }
    }
}

