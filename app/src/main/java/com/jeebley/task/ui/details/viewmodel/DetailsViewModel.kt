package com.jeebley.task.ui.details.viewmodel

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

class DetailsViewModel @Inject constructor() : ViewModel() {

    var categoryArrayDetailsLiveData: MutableLiveData<Response.ListCategory> = MutableLiveData()

     fun getDetails(context: Context, rId: String): Boolean {
        APIManager
                .service<UserService>()
                .getDetailed("menuCategories", rId, 1, 1, 21)
                .runOnBackgroundObserveOnMain()
                .onStart { Progress.show(context, context.getString(R.string.loading)) }
                .subscribe(
                        { result ->
                            categoryArrayDetailsLiveData.value = result
                                    .apply { Progress.dismiss() }
                        },
                        {
                            categoryArrayDetailsLiveData.value = null
                            apply { Progress.dismiss() }
                        }
                )

        return true
    }

}