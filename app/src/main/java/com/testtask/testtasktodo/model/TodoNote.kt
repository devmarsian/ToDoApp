package com.testtask.testtasktodo.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todonotes")
data class TodoNote(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val description: String,
    var isCompleted: Boolean
)
