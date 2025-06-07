package com.example.schedulemanagerapp.data.room

import androidx.room.*
import java.time.LocalDate

@Dao
interface TestDao {
    @Query("SELECT * FROM tests WHERE date BETWEEN :from AND :to ORDER BY date ASC")
    suspend fun getUpcomingTests(from: LocalDate, to: LocalDate): List<TestEntity>

    @Insert
    suspend fun insert(test: TestEntity)
}
