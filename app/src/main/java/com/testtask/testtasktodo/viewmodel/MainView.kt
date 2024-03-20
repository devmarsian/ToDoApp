package com.testtask.testtasktodo.viewmodel

import com.testtask.testtasktodo.model.TodoNote
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface MainView : MvpView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showLoading()
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun hideLoading()
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showTodoList(todoList: List<TodoNote>)
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showDoneTasksButton()
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun hideDoneTasksButton()
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showMessage(message: String)
}