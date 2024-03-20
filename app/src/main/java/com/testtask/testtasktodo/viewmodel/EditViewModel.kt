package com.testtask.testtasktodo.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testtask.testtasktodo.model.TodoDatabase
import com.testtask.testtasktodo.model.TodoNote
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import moxy.MvpPresenter
import javax.inject.Inject

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class EditPresenter @Inject constructor(
    private val todoDatabase: TodoDatabase
) : MvpPresenter<EditView>() {
    fun setDescription(description: String?) {
        viewState.setDescription(description)
    }
    val textChangesSubject: PublishSubject<String> = PublishSubject.create()

    @SuppressLint("CheckResult")
    fun updateTextInDatabase(noteId: Long, newDescription: String) {
        updateDescriptionInDatabase(noteId, capitalizeSentences(newDescription))
            .subscribeOn(Schedulers.io())
            .subscribe(
                { viewState.showMessage("Task updated successfully") },
                { error -> }
            )
    }

    @SuppressLint("CheckResult")
    fun saveTask(description: String, id: Long) {
        val todoNote = TodoNote(
            id = id,
            description = capitalizeSentences(description),
            isCompleted = false,
            isNotificationSet = false
        )
        if (description.isNotEmpty()) {
            insertTodoToDatabase(todoNote)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { viewState.showMessage("Task saved successfully") },
                    { error -> viewState.showMessage(error.message.toString())}
                )
        }
    }
    private fun insertTodoToDatabase(todoNote: TodoNote): Completable {
        return Completable.fromAction {
            todoDatabase.todoDao().insert(todoNote)
        }
    }

    @SuppressLint("CheckResult")
    fun deleteFromDB(noteId: Long) {
        deleteTodoFromDatabase(noteId)
            .subscribeOn(Schedulers.io())
            .subscribe(
                { viewState.showMessage("Task deleted successfully") },
                { error -> }
            )
    }

    fun capitalizeSentences(description: String): String {
        val sentences = description.split("(?<=[.!?])".toRegex())
        val capitalizedSentences = sentences.map { sentence ->
            sentence.trim().replaceFirstChar { it.uppercaseChar() }
        }
        return capitalizedSentences.joinToString("")
    }

    private fun updateDescriptionInDatabase(noteId: Long, newDescription: String): Completable {
        return Completable.fromAction {
            todoDatabase.todoDao().updateDescription(noteId, newDescription)
        }
    }

    private fun deleteTodoFromDatabase(noteId: Long): Completable {
        return Completable.fromAction {
            todoDatabase.todoDao().deleteById(noteId)
        }
    }

    private fun updateNotificationStatus(noteId: Long, isNotificationSet: Boolean): Completable {
        return Completable.fromCallable {
            todoDatabase.todoDao().updateNotificationStatus(noteId, isNotificationSet)
        }
    }

    private fun isNotificationSetForTodo(noteId: Long): Single<Boolean> {
        return todoDatabase.todoDao().isNotificationSetForTodo(noteId)
            .map { count -> count > 0 }
    }

    @SuppressLint("CheckResult")
    fun toggleNotificationForTodo(noteId: Long) {
        isNotificationSetForTodo(noteId)
            .flatMapCompletable { isSet ->
                if (isSet) {
                    updateNotificationStatus(noteId, false)
                    Completable.complete()
                } else {
                    updateNotificationStatus(noteId, true)
                        .andThen(sendNotificationForTodo())
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    viewState.showMessage("Уведомление установлено")
                },
                { error ->
                    viewState.showMessage("Ошибка при установке")
                }
            )
    }

    fun observeTextChanges(): Observable<String> {
        return textChangesSubject
            .debounce(3000, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
    }
    fun onTextChanged(text: String) {
        textChangesSubject.onNext(text)
    }
    private fun sendNotificationForTodo(): Completable {
        viewState.setPush()
        return Completable.complete()
    }
}

