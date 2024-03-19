package com.testtask.testtasktodo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testtask.testtasktodo.model.TodoDatabase
import com.testtask.testtasktodo.model.TodoNote
import kotlinx.coroutines.launch

class EditViewModel(private val todoDatabase: TodoDatabase) : ViewModel() {

    val description: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    var titleText: String = "Новая заметка"

    fun setDescription(description: String?) {
        this.description.value = description
    }


    fun saveTask(description: String) {
        val todoNote = TodoNote(
            description = description,
            isCompleted = false
        )
        viewModelScope.launch {
            todoDatabase.todoDao().insert(todoNote)
        }
    }

    fun updateTextInDatabase(noteId: Long, newDescription: String) {
        viewModelScope.launch {
            todoDatabase.todoDao().updateDescription(noteId, newDescription)
        }
    }

    fun capitalizeSentences(description: String): String {
        val sentences = description.split("(?<=[.!?])".toRegex())
        val capitalizedSentences = sentences.map { sentence ->
            sentence.trim().replaceFirstChar { it.uppercaseChar() }
        }
        return capitalizedSentences.joinToString("")
    }

    fun deleteFromDB(noteId: Long) {
        viewModelScope.launch {
            todoDatabase.todoDao().deleteById(noteId)
        }
    }
}