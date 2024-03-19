package com.testtask.testtasktodo.di

import com.testtask.testtasktodo.view.EditFragment
import com.testtask.testtasktodo.view.MainFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DatabaseModule::class, ViewModelModule::class])
interface AppComponent {

    fun inject(fragment: MainFragment)

    fun inject(fragment: EditFragment)
}