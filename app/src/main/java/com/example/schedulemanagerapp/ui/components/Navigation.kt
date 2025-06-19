package com.example.schedulemanagerapp.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.schedulemanagerapp.ui.screens.*
import com.google.firebase.auth.FirebaseAuth
import java.time.DayOfWeek


object Routes {
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val COURSE_LIST = "course_list"
    const val ADD_COURSE = "add_course"
    const val ADD_TEST = "add_test"
    const val ADD_SCHEDULE = "add_schedule"
    const val ADD_ASSIGNMENT = "add_assignment/{courseCode}"
    const val DAILY_SCHEDULE = "daily_schedule/{day}"
    const val ASSIGNMENT_LIST = "assignment_list/{courseCode}"
    const val OVERVIEW = "overview"
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScheduleNavHost(navController: NavHostController = rememberNavController()) {
    val currentUser = FirebaseAuth.getInstance().currentUser

    NavHost(
        navController = navController,
        startDestination = if (currentUser == null) Routes.LOGIN else Routes.COURSE_LIST
    ) {
        composable(Routes.LOGIN) {
            LoginScreen(
                navController = navController,
                onLoginSuccess = {
                    navController.navigate(Routes.COURSE_LIST) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.REGISTER) {
            RegisterScreen(navController = navController)
        }
        composable(Routes.COURSE_LIST) {
            ScheduleScaffold(navController = navController) {
                CourseListScreen(navController = navController)
            }
        }
        composable(Routes.ADD_COURSE) {
            AddCourseScreen(navController)
        }
        composable(Routes.ADD_TEST) {
            AddTestScreen(navController)
        }
        composable(Routes.ADD_SCHEDULE) {
            AddScheduleScreen(navController)
        }
        composable(Routes.ADD_ASSIGNMENT, arguments = listOf(navArgument("courseCode") { type = NavType.StringType })) {
            val courseCode = it.arguments?.getString("courseCode") ?: return@composable
            AddAssignmentScreen(courseCode = courseCode, navController = navController)
        }
        composable(Routes.ASSIGNMENT_LIST, arguments = listOf(navArgument("courseCode") { type = NavType.StringType })) {
            val courseCode = it.arguments?.getString("courseCode") ?: return@composable
            AssignmentListScreen(courseCode = courseCode)
        }
        composable(Routes.DAILY_SCHEDULE, arguments = listOf(navArgument("day") { type = NavType.StringType })) {
            val day = DayOfWeek.valueOf(it.arguments?.getString("day") ?: return@composable)
            DailyScheduleScreen(selectedDay = day)
        }
        composable(Routes.OVERVIEW) {
            ScheduleOverviewScreen()
        }
    }
}