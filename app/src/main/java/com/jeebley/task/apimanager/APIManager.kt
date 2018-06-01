package com.jeebley.task.apimanager


import com.jeebley.task.BuildConfig
import com.jeebley.task.api.retrofit.RxRetrofit

class APIManager {
    companion object {
        var rxRetrofit = RxRetrofit()
        var map = HashMap<String, Any>()

        fun isSuccess(errorCode: Int?): Boolean = (errorCode == 0)

        inline fun <reified T> service(): T {
            return if (map.containsKey(T::class.toString())) {
                map[T::class.toString()] as T
            } else {
                var cls = rxRetrofit.create(BuildConfig.URL_ENDPOINT, T::class.java)

                map[T::class.toString()] = cls as Any

                cls
            }
        }
    }
}

