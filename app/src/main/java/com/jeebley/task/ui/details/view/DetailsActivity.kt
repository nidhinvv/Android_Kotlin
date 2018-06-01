package com.jeebley.task.ui.details.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.jakewharton.rxbinding.view.clicks
import com.jeebley.task.R
import com.jeebley.task.adapter.RxAdapter
import com.jeebley.task.adapter.SimpleViewHolder
import com.jeebley.task.api.responce.Response
import com.jeebley.task.app.Constant
import com.jeebley.task.extensions.initializeToolBar
import com.jeebley.task.ui.base.BaseActivity
import com.jeebley.task.ui.details.viewmodel.DetailsViewModel
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.item_detailed_child.view.*
import kotlinx.android.synthetic.main.item_detailed_parent.view.*
import javax.inject.Inject

class DetailsActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var detailsViewModel: DetailsViewModel
    private var rId = ""
    private var workingHour = ""
    private var rName = ""
    private lateinit var rxAdapter: RxAdapter<Response.CategoryArray, SimpleViewHolder<Response.CategoryArray>>
    private lateinit var rxAdapterChild: RxAdapter<Response.MenuArray, SimpleViewHolder<Response.MenuArray>>
    private var categoryArray: MutableList<Response.CategoryArray> = ArrayList()
    private var menuArray: MutableList<Response.MenuArray> = ArrayList()
    private var prevSelected: Int = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        initializeToolBar(toolbar, "")
        enableBackButton()
        val mIntent = intent
        rId = mIntent.getStringExtra(Constant.REST_ID)
        workingHour = mIntent.getStringExtra(Constant.REST_WORKING_HOUR)
        rName = mIntent.getStringExtra(Constant.REST_NAME)
        initialize()
        initObservers()
    }

    private fun initialize() {
        tv_time_rest.text = workingHour
        txt_rest_name.text = rName
        detailsViewModel = ViewModelProviders.of(this, viewModelFactory).get(DetailsViewModel::class.java)
        detailsViewModel.getDetails(this, rId)
        setCategory()
    }


    private fun initObservers() {
        detailsViewModel.categoryArrayDetailsLiveData.observe(this, Observer {
            updateAdapter(it!!.categoryArray)
        })
    }

    private fun setCategory() {
        rxAdapter = RxAdapter(categoryArray, R.layout.item_detailed_parent)
        rxAdapter
                .bindRecyclerView(rv_content, LinearLayoutManager(this))
                .asObservable()
                .subscribe { result ->
                    result.view.tag = result.item
                    result.view.tv_parentName.text = result.item?.menuName_eng

                    menuArray = result.item?.menuArray as MutableList<Response.MenuArray>
                    setMenuItems(result.view.rv_child)

                    if (result.item!!.isSelected) {
                        result.view.rv_child.visibility = View.VISIBLE
                    } else {
                        result.view.rv_child.visibility = View.GONE
                    }
                    result.view.tv_parentName.clicks().subscribe {
                        val selectedPosition: Int = result.pos
                        if (prevSelected == -1) {
                            categoryArray[selectedPosition].isSelected = true
                        } else if (selectedPosition == prevSelected) {
                            categoryArray[selectedPosition].isSelected = !categoryArray[selectedPosition].isSelected
                        } else {
                            categoryArray[selectedPosition].isSelected = true
                            categoryArray[prevSelected].isSelected = false
                        }
                        prevSelected = selectedPosition
                        rxAdapter.notifyDataSetChanged()
                    }
                }
    }

    private fun setMenuItems(recycler: RecyclerView) {
        rxAdapterChild = RxAdapter(menuArray, R.layout.item_detailed_child)
        rxAdapterChild
                .bindRecyclerView(recycler, LinearLayoutManager(this))
                .asObservable()
                .subscribe { result1 ->
                    result1.view.tag = result1.item
                    result1.view.tv_child_title.text = result1.item?.itemName_eng
                    result1.view.tv_child_desc.text = result1.item?.itemDesc_eng
                    result1.view.tv_child_amount.text = result1.item?.itemPrice
                    result1.view.iv_child.setImageURI(result1.item?.itemImage)

                }
    }

    private fun updateAdapter(list: List<Response.CategoryArray>): Boolean {
        categoryArray = list as MutableList<Response.CategoryArray>
        rxAdapter.updateDataSet(list)
        return true
    }
}