package com.example.schedulemanagerapp.data.room

import com.example.schedulemanagerapp.data.room.Converters
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [CourseEntity::class, ClassScheduleEntity::class, TestEntity::class, AssignmentEntity::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun courseDao(): CourseDao
    abstract fun scheduleDao(): ScheduleDao
    abstract fun testDao(): TestDao
    abstract fun assignmentDao(): AssignmentDao
}


