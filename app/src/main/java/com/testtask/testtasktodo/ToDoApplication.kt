package com.testtask.testtasktodo

import android.app.Application
import com.testtask.testtasktodo.di.AppComponent
import com.testtask.testtasktodo.di.AppModule
import com.testtask.testtasktodo.di.DaggerAppComponent


class ToDoApplication : Application() {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this@ToDoApplication)).build()
    }

    fun getAppComponentSynthetic(): AppComponent {
        return appComponent
    }
}