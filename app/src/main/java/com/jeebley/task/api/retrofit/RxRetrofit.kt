package com.jeebley.task.api.retrofit

import com.google.gson.GsonBuilder
import com.jeebley.task.BuildConfig
import okhttp3.EventListener
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory



class RxRetrofit{
    private var eventListener = RetrofitEventListener()

    fun <T> create(baseUrl: String, service: Class<T>): T{
        val gson = GsonBuilder().setLenient().create()
        lateinit var retrofit: Retrofit

        if(BuildConfig.IS_LOG_ENABLED){
            val logging = HttpLoggingInterceptor()
            // set your desired log level
            logging.level = HttpLoggingInterceptor.Level.BODY

            retrofit = createRetrofit(
                    baseUrl,
                    logging,
                    authorizeInterceptor(),
                    ScalarsConverterFactory.create(),
                    GsonConverterFactory.create(gson),
                    RxJava2CallAdapterFactory.create(),
                    eventListener)
        }
        else{
            retrofit = createRetrofit(
                    baseUrl,
                    authorizeInterceptor(),
                    ScalarsConverterFactory.create(),
                    GsonConverterFactory.create(gson),
                    RxJava2CallAdapterFactory.create(),
                    eventListener)
        }

        return retrofit.create(service)
    }

    private fun createRetrofit(vararg params: Any): Retrofit{
        var retrofitBuilder = Retrofit.Builder()
        var okHttpBuilder = OkHttpClient.Builder()

        for(param in params){
            when(param){
                is Interceptor -> { okHttpBuilder.interceptors().add(param) }
                is EventListener -> { okHttpBuilder.eventListener(param) }
                is Converter.Factory -> { retrofitBuilder.converterFactories().add(param) }
                is CallAdapter.Factory -> { retrofitBuilder.callAdapterFactories().add(param) }
                is String -> { retrofitBuilder.baseUrl(param) }
                is HttpUrl -> { retrofitBuilder.baseUrl(param) }
            }
        }

        return retrofitBuilder.client(okHttpBuilder.build()).build()
    }

    private fun authorizeInterceptor(): Interceptor {
        return Interceptor { chain ->
            val requestOriginal = chain.request()
            val customAnnotations = requestOriginal.headers().values("Authorization")
            val newBuilder = requestOriginal.newBuilder()
            val token = if (customAnnotations.size > 0) customAnnotations[0] else ""

            takeIf { customAnnotations.size > 0 && token.isNotEmpty() }?.apply {
                newBuilder.removeHeader("Authorization")
                newBuilder.addHeader("Authorization", "Bearer $token")
            }

            val request = newBuilder
                    .addHeader("Content-Type", "application/json; charset=UTF-8")
                    .build()

            chain.proceed(request)
        }
    }
}