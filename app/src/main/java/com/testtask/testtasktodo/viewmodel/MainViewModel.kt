package com.testtask.testtasktodo.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testtask.testtasktodo.model.TodoDatabase
import com.testtask.testtasktodo.model.TodoNote
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel(private val todoDatabase: TodoDatabase) : ViewModel() {
    private val _todoList = MutableLiveData<List<TodoNote>>()
    val todoList: LiveData<List<TodoNote>> = _todoList
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun loadData() {
        viewModelScope.launch {
            _isLoading.value = true
            delay(1500)
            val todos = todoDatabase.todoDao().getAllNotes()
            _todoList.value = todos
            _isLoading.value = false
        }
    }
}
