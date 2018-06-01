package com.jeebley.task.di

import android.arch.lifecycle.ViewModel
import com.jeebley.task.ui.details.viewmodel.DetailsViewModel
import com.jeebley.task.ui.main.viewmodel.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    // Bind your View Response here
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel::class)
    // Bind your View Response here
    abstract fun bindDetailsViewModel(detailsViewModel: DetailsViewModel): ViewModel


}