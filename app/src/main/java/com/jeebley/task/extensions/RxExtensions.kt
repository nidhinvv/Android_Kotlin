package com.jeebley.task.extensions

import android.view.View
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.net.ConnectException
import java.net.SocketTimeoutException

inline fun <T> Observable<T>.runOnBackgroundObserveOnMain(): Observable<T> {
    return this.subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())
}

fun <T> Observable<T>.onStart(onSubscribe: () -> Unit): Observable<T> {
    return this.doOnSubscribe ({ onSubscribe() })
}

fun Throwable.handleError(view: View){
    view.snack(this.getMessage())
}

fun Throwable.getMessage(): String{
    return when(this){
        is ConnectException -> "Internet connectivity is not available."
        is SocketTimeoutException -> "Cannot contact server, please try again later."
        else -> this?.message ?: "An error occurred, please try again later."
    }
}