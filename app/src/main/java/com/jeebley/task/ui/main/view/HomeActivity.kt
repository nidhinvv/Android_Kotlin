package com.jeebley.task.ui.main.view

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.view.View
import com.jakewharton.rxbinding.view.clicks
import com.jeebley.task.R
import com.jeebley.task.api.responce.Response
import com.jeebley.task.app.Constant
import com.jeebley.task.ui.base.BaseActivity
import com.jeebley.task.ui.details.view.DetailsActivity
import com.jeebley.task.ui.main.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class HomeActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var homeViewModel: HomeViewModel
    private lateinit var stores: Response.StoreDetails

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialize()
        initObservers()
    }

    private fun initialize() {

        homeViewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java)
        homeViewModel.getDetails(this)
        iv_main.clicks().subscribe {
            moveToDetails(iv_main)
        }
    }

    private fun initObservers() {
        homeViewModel.storeDetailsLiveData.observe(this, Observer {
            stores = it!!
            setData(stores)
        })
    }

    private fun moveToDetails(view: View) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(Constant.REST_ID, stores.restaurantAreaInfo?.rId)
        intent.putExtra(Constant.REST_WORKING_HOUR, stores.restaurantAreaInfo?.workingHour)
        intent.putExtra(Constant.REST_NAME, stores.restaurantAreaInfo?.rName)
        val p1 = Pair(view, getString(R.string.image_store_detailed))
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this as Activity, p1)
        startActivity(intent, options.toBundle())
    }

    private fun setData(result: Response.StoreDetails) {
        stores = result
        tv_name.text = stores.restaurantAreaInfo?.rName
        tv_time.text = stores.restaurantAreaInfo?.rDeliveryTime
        tv_type.text = stores.restaurantAreaInfo?.cuisineTitle1
    }
}
