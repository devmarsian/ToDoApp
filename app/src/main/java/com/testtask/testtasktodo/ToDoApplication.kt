package com.testtask.testtasktodo

import android.app.Application
import com.testtask.testtasktodo.di.databaseModule
import com.testtask.testtasktodo.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ToDoApplication: Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ToDoApplication)
            modules(databaseModule,viewModelModule)
        }
    }
}