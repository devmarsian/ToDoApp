package com.testtask.testtasktodo.di

import android.app.Application
import com.testtask.testtasktodo.model.TodoDatabase
import com.testtask.testtasktodo.view.EditFragment
import com.testtask.testtasktodo.view.MainFragment
import com.testtask.testtasktodo.view.TodoAdapter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DatabaseModule::class, AppModule::class])
interface AppComponent {
    fun provideTodoDatabase(): TodoDatabase

    fun inject(application: Application)

    fun inject(fragment: MainFragment)

    fun inject(fragment: EditFragment)
}


