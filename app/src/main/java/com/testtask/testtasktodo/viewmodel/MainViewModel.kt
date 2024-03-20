package com.testtask.testtasktodo.viewmodel

import com.testtask.testtasktodo.model.TodoDatabase
import com.testtask.testtasktodo.model.TodoNote
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpPresenter
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainPresenter @Inject constructor(
                    private val todoDatabase: TodoDatabase
)  : MvpPresenter<MainView>() {
    private var showDoneTasks = false
    private val compositeDisposable = CompositeDisposable()
    fun loadData() {
        viewState.showLoading()
        val disposable = Observable.fromCallable {
            val incompleteTasks = todoDatabase.todoDao().getIncompleteTasks()
            if (showDoneTasks) {
                val completedTasks = todoDatabase.todoDao().getCompletedTasks()
                incompleteTasks + completedTasks
            } else {
                incompleteTasks
            }
        }
            .delay(1500, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ todos ->
                viewState.showTodoList(todos)
                viewState.hideLoading()
                checkDb()
            }, { _ ->
                viewState.hideLoading()
            })
        compositeDisposable.add(disposable)
    }

    fun markTaskAsDone(todo: TodoNote) {
//        todo.isCompleted = true
//        val disposable = Completable.fromAction {
//            todoDatabase.todoDao().updateStatus(todo.id, true)
//        }
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                loadData()
//            }, { error ->
//            })
//        compositeDisposable.add(disposable)
    }



    private fun checkDb() {
        val completedTasks = todoDatabase.todoDao().getCompletedTasks()
        if (showDoneTasks && completedTasks.isNotEmpty()) {
            viewState.showDoneTasksButton()
        } else {
            viewState.hideDoneTasksButton()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    fun toggleDoneTasksVisibility() {
        showDoneTasks = !showDoneTasks
        loadData()
    }
}
