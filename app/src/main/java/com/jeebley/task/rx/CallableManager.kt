package com.jeebley.task.rx

import io.reactivex.Observable
import io.reactivex.Observable.fromCallable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CallableManager {
    interface CallableWithArgs {
        fun call(params: Array<out Any>): Any
    }

    companion object {
        inline fun <reified T : CallableWithArgs, R> call(vararg params: Any): Observable<R> {
            return fromCallable({ T::class.java.newInstance().call(params) as R }).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())
        }
    }
}