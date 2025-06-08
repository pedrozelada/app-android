package com.example.schedulemanagerapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.example.schedulemanagerapp.ui.components.ScheduleNavHost
import com.example.schedulemanagerapp.ui.theme.ScheduleManagerAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScheduleManagerAppTheme {
                ScheduleNavHost()
            }
        }
    }
}