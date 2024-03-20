package com.testtask.testtasktodo.di

import android.app.Application
import com.testtask.testtasktodo.model.TodoDao
import com.testtask.testtasktodo.model.TodoDatabase
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
class AppModule(private val application: Application) {
    @Provides
    @Singleton
    fun provideApplication(): Application {
        return application
    }
}

