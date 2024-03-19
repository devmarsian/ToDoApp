package com.testtask.testtasktodo.di

import android.content.Context
import androidx.room.Room
import com.testtask.testtasktodo.model.TodoDatabase

object DatabaseBuilder {
    private var INSTANCE: TodoDatabase? = null

    fun getInstance(context: Context): TodoDatabase {
        if (INSTANCE == null) {
            synchronized(TodoDatabase::class) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    TodoDatabase::class.java,
                    "tododb"
                ).build()
            }
        }
        return INSTANCE!!
    }
}