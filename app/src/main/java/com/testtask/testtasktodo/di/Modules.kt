package com.testtask.testtasktodo.di

import android.app.Application
import androidx.lifecycle.ViewModel
import com.testtask.testtasktodo.model.TodoDao
import com.testtask.testtasktodo.model.TodoDatabase
import com.testtask.testtasktodo.viewmodel.EditViewModel
import com.testtask.testtasktodo.viewmodel.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(application: Application): TodoDatabase {
        return DatabaseBuilder.getInstance(application)
    }

    @Singleton
    @Provides
    fun provideTodoDao(database: TodoDatabase): TodoDao {
        return database.todoDao()
    }
}


@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    abstract fun bindEditViewModel(viewModel: EditViewModel): ViewModel
}