package com.example.schedulemanagerapp.data.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.time.DayOfWeek

@Dao
interface ScheduleDao {
    @Query("SELECT * FROM schedules WHERE day = :day")
    fun getSchedulesByDay(day: DayOfWeek): Flow<List<ClassScheduleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(schedule: ClassScheduleEntity)
}
