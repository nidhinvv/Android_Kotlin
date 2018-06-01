package com.jeebley.task.extensions

import android.util.Patterns

fun String.isSame(str: String): Boolean = (this == str)

fun String.isValidEmail(): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(this).matches()
}