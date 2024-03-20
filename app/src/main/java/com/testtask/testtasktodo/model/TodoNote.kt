package com.testtask.testtasktodo.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todonotes")
data class TodoNote(
    @PrimaryKey
    val id: Long = 0,
    val description: String,
    var isCompleted: Boolean,
    var isNotificationSet: Boolean
)
