package com.example.schedulemanagerapp.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tests")
data class TestEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val courseCode: String,
    val topic: String,
    val date: String,
    val place: String = ""
)
