package com.testtask.testtasktodo

import android.app.Application
import com.testtask.testtasktodo.di.AppComponent
import com.testtask.testtasktodo.di.DatabaseModule
import com.testtask.testtasktodo.di.databaseModule
import com.testtask.testtasktodo.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ToDoApplication: Application(){
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .databaseModule(DatabaseModule())
            .build()
    }
}