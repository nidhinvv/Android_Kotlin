package com.jeebley.task.di

import android.content.Context
import com.jeebley.task.app.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    fun providesContext(application: App): Context = application
}