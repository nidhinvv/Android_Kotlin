package com.jeebley.task.di

import android.arch.lifecycle.ViewModelProvider
import com.jeebley.task.ui.details.view.DetailsActivity
import com.jeebley.task.ui.main.view.HomeActivity
import com.jeebley.task.ui.splash.SplashActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @ContributesAndroidInjector()
    internal abstract fun contributeMainActivity(): HomeActivity

    @ContributesAndroidInjector()
    internal abstract fun contributeSplashActivity(): SplashActivity

    @ContributesAndroidInjector()
    internal abstract fun contributeDetailsActivity(): DetailsActivity


}