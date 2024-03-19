package com.testtask.testtasktodo.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TodoNote::class], version = 1)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}