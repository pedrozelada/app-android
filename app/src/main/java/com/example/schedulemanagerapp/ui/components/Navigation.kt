package com.example.schedulemanagerapp.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.schedulemanagerapp.ui.screens.CourseListScreen
import com.example.schedulemanagerapp.ui.screens.AddCourseScreen
import com.example.schedulemanagerapp.ui.screens.AddTestScreen


object Routes {
    const val COURSE_LIST = "course_list"
    const val ADD_COURSE = "add_course"
    const val ADD_TEST = "add_test"
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScheduleNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Routes.COURSE_LIST) {
        composable(Routes.COURSE_LIST) {
            CourseListScreen(navController = navController)
        }
        composable(Routes.ADD_COURSE) {
            AddCourseScreen(navController)
        }
        composable(Routes.ADD_TEST) {
            AddTestScreen(navController)
        }
    }
}
