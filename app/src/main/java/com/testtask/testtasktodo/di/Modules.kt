package com.testtask.testtasktodo.di

import com.testtask.testtasktodo.model.TodoDatabase
import com.testtask.testtasktodo.viewmodel.EditViewModel
import com.testtask.testtasktodo.viewmodel.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val databaseModule = module {
    single {
        DatabaseBuilder.getInstance(androidContext())
    }
    single {
        get<TodoDatabase>().todoDao()
    }
}


val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { EditViewModel(get()) }
}