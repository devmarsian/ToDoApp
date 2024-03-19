package com.testtask.testtasktodo.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TodoDao {
    @Query("SELECT * FROM todonotes")
    suspend fun getAllNotes(): List<TodoNote>

    @Insert
    suspend fun insert(note: TodoNote)

    @Query("DELETE FROM todonotes WHERE id = :noteId")
    suspend fun deleteById(noteId: Long)

    @Query("UPDATE todonotes SET description = :newDescription WHERE id = :noteId")
    suspend fun updateDescription(noteId: Long, newDescription: String)
}