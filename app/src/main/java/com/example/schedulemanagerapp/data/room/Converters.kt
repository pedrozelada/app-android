package com.example.schedulemanagerapp.data.room

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime

class Converters {
    @TypeConverter
    fun fromDayOfWeek(day: DayOfWeek): String = day.name

    @TypeConverter
    fun toDayOfWeek(value: String): DayOfWeek = DayOfWeek.valueOf(value)

    @TypeConverter
    fun fromLocalDate(date: LocalDate): String = date.toString()

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun toLocalDate(value: String): LocalDate = LocalDate.parse(value)

    @TypeConverter
    fun fromLocalTime(time: LocalTime): String = time.toString()

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun toLocalTime(value: String): LocalTime = LocalTime.parse(value)
}
