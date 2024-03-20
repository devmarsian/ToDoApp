package com.testtask.testtasktodo.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import io.reactivex.rxjava3.core.Single

@Dao
interface TodoDao {
    @Query("SELECT * FROM todonotes")
     fun getAllNotes(): List<TodoNote>
    @Insert
     fun insert(note: TodoNote)
    @Query("DELETE FROM todonotes WHERE id = :noteId")
     fun deleteById(noteId: Long)
    @Query("UPDATE todonotes SET description = :newDescription WHERE id = :noteId")
     fun updateDescription(noteId: Long, newDescription: String)
     @Query("UPDATE todonotes SET isCompleted = :isCompleted WHERE id = :noteId")
     fun updateStatus(noteId: Long, isCompleted: Boolean)
    @Query("SELECT * FROM todonotes WHERE isCompleted = 0")
    fun getIncompleteTasks(): List<TodoNote>
    @Query("SELECT * FROM todonotes WHERE isCompleted = 1")
    fun getCompletedTasks(): List<TodoNote>
    @Query("UPDATE todonotes SET isNotificationSet = :isNotificationSet WHERE id = :noteId")
    fun updateNotificationStatus(noteId: Long, isNotificationSet: Boolean)
    @Query("SELECT COUNT(*) FROM todonotes WHERE id = :noteId AND isNotificationSet = 1")
    fun isNotificationSetForTodo(noteId: Long): Single<Int>
}