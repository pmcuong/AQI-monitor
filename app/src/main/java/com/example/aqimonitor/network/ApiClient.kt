package com.example.aqimonitor.network

import com.example.aqimonitor.utils.Constant
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.util.concurrent.TimeUnit

object ApiClient {

    private const val TIME_OUT: Long = 15

    private var mOkHttpClient: OkHttpClient? = null
    private var mGsonConverter: GsonConverterFactory? = null
    private var mRetrofit: Retrofit? = null

    /**
     * Don't forget to remove Interceptors (or change Logging Level to NONE)
     * in production! Otherwise people will be able to see your request and response on Log Cat.
     */
    private val okHttpClient: OkHttpClient
        @Throws(NoSuchAlgorithmException::class, KeyManagementException::class)
        get() {
            if (mOkHttpClient == null) {
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BODY

                val httpBuilder = OkHttpClient.Builder()
                httpBuilder
                    .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                    .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                    .addInterceptor(interceptor)  /// show all JSON in logCat
//                    .addInterceptor { chain ->
//                        val original = chain.request()
//                        val requestBuilder = original.newBuilder()
//                        var newUrl = original.url().url().toString()
//                        if(original.url().queryParameter("token").isNullOrBlank()) {
//                            newUrl += "?token="
//                        }
//                            requestBuilder.url(newUrl)
//
//
//                            .addHeader("Accept", "application/json")
//                            .addHeader("Content-Type", "application/json")
//
//
//                        // Adding Authorization token (API Key)
//                        // Requests will be denied without API key
//                        // Adding Authorization token (API Key)
//                        // Requests will be denied without API key
////                        if (!TextUtils.isEmpty(PrefUtils.getApiKey(context))) {
////                            requestBuilder.addHeader("Authorization", PrefUtils.getApiKey(context))
////                        }
//
//                        chain.proceed(requestBuilder.build())
//                    }

                mOkHttpClient = httpBuilder.build()

            }
            return mOkHttpClient!!
        }


    private val gsonConverter: GsonConverterFactory
        get() {
            if (mGsonConverter == null) {
                mGsonConverter = GsonConverterFactory
                    .create(
                        GsonBuilder()
                            .setLenient()
                            .disableHtmlEscaping()
                            .create()
                    )
            }
            return mGsonConverter!!
        }

    val client: Retrofit
        get() {
            if (mRetrofit == null) {
                mRetrofit = Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(gsonConverter)
                    .client(okHttpClient)
                    .build()
            }
            return mRetrofit!!
        }

}