package com.testtask.testtasktodo.viewmodel

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface EditView : MvpView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setDescription(description: String?)
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showMessage(message: String)
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setPush()
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setupEmptyDescription(id: Long)
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setupNonEmptyDescription(id:Long)
}
