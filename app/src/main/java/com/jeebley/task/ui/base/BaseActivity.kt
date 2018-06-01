package com.jeebley.task.ui.base

import android.support.v4.app.FragmentManager
import android.view.View
import com.jeebley.task.extensions.hideKeyboard
import dagger.android.support.DaggerAppCompatActivity


open class BaseActivity : DaggerAppCompatActivity(), FragmentManager.OnBackStackChangedListener {

    private val backStackCount: Int = 1
    private var toolBarHolder: View? = null

    protected fun hideToolBarHolder() {
        toolBarHolder?.visibility = View.GONE
    }

    protected fun setToolBarHolder(view: View) {
        this.toolBarHolder = view
    }

    protected fun setToolBarHolder(view: View, visibility: Boolean) {
        this.toolBarHolder = view
        val state: Int = if (visibility) View.VISIBLE else View.GONE
        toolBarHolder?.visibility = state
    }

    protected fun enableBackButton() {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onBackStackChanged() {
        supportActionBar?.setHomeButtonEnabled(supportFragmentManager.backStackEntryCount > backStackCount)
    }

    override fun onBackPressed() {
        manageBack()
    }

    override fun onSupportNavigateUp(): Boolean {
        manageBack()

        return true
    }

    private fun manageBack() {
        currentFocus?.hideKeyboard()

        if (supportFragmentManager.backStackEntryCount > backStackCount) {
            supportFragmentManager?.popBackStack()
        } else {
            finish()
        }
    }
}