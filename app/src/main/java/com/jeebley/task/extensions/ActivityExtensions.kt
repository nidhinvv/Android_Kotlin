package com.jeebley.task.extensions

import android.app.Activity
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.jeebley.task.app.App




/**
 * Extension property to get application from Activity
 *
 * @returns application
 * @sample:
 *      app
 */
val Activity.app: App
    get() = application as App

/**
 * Begin Fragment Transaction
 *
 * Used in helper methods addFragment and removeFragment
 *
 * Shamelessly copied from
 * @link https://medium.com/thoughts-overflow/how-to-add-a-fragment-in-kotlin-way-73203c5a450b
 */
inline fun FragmentManager.beginTransaction(op: FragmentTransaction.() -> FragmentTransaction) {
    val fragmentTransaction = beginTransaction()
    fragmentTransaction.op()
    fragmentTransaction.commit()
}

fun AppCompatActivity.addFragment(tag: String, fragment: Fragment, frameId: Int) {
    if (null == supportFragmentManager.findFragmentByTag(tag)) {
        supportFragmentManager.beginTransaction { add(frameId, fragment, tag) }
    }
}

fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int) {
    supportFragmentManager.beginTransaction { replace(frameId, fragment) }
}

fun AppCompatActivity.initializeToolBar(toolBar: Toolbar?, title: String) {
    toolBar?.title = ""
    setSupportActionBar(toolBar)
    supportActionBar?.title = title
}



