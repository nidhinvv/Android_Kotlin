package com.jeebley.task.extensions

import java.util.*

operator fun <T, R, V> ((T) -> R).rangeTo(other: (R) -> V): ((T) -> V) {
    return {
        other(this(it))
    }
}

infix fun Boolean.doIfTrue(block: () -> Boolean): Boolean{
    return if (this) block() else false
}

infix fun Boolean.doIfFalse(block: () -> Boolean): Boolean{
    return if (!this) block() else this
}

infix fun Boolean.elseDo(block: () -> Unit): Boolean{
    if (!this) block()

    return this
}

inline fun Any.BooleanFunc(block: () -> Unit): Boolean{
    block()
    return true
}

inline fun Calendar.isBefore(date: Calendar): Boolean{
    return this.before(date)
}