package com.jeebley.task.ui.main.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import com.jeebley.task.R
import com.jeebley.task.api.responce.Response
import com.jeebley.task.api.service.UserService
import com.jeebley.task.apimanager.APIManager
import com.jeebley.task.extensions.onStart
import com.jeebley.task.extensions.runOnBackgroundObserveOnMain
import com.jeebley.task.utils.Progress
import javax.inject.Inject


class HomeViewModel @Inject constructor() : ViewModel() {
    var storeDetailsLiveData: MutableLiveData<Response.StoreDetails> = MutableLiveData()

    fun getDetails(context: Context): Boolean {

        APIManager
                .service<UserService>()
                .getStore("restaurantAreaInfo", 1, 21, 1, 366)
                .runOnBackgroundObserveOnMain()
                .onStart { Progress.show(context, context.getString(R.string.loading)) }
                .subscribe(
                        { result ->
                            storeDetailsLiveData.value = result
                                    .apply { Progress.dismiss() }
                        },
                        {
                            storeDetailsLiveData.value = null
                            apply { Progress.dismiss() }
                        }
                )
        return true

    }
}