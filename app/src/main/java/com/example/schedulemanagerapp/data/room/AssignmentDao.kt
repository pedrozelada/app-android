package com.example.schedulemanagerapp.data.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AssignmentDao {
    @Query("SELECT * FROM assignments WHERE courseCode = :code ORDER BY dueDate ASC")
    fun getAssignmentsByCourse(code: String): Flow<List<AssignmentEntity>>

    @Insert
    suspend fun insert(assignment: AssignmentEntity)
}
