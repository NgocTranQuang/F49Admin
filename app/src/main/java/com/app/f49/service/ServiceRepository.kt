package com.app.f49.service

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.app.f49.BuildConfig
import com.app.f49.utils.Constants
import com.app.f49.utils.GeneralUtils
import com.app.f49.utils.PreferenceUtils
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit


class ServiceRepository {
    companion object {

        private val CACHE_SIZE = (10 * 1024 * 1024).toLong()
        private val CACHE_CONTROL = "Cache-Control"
        private val TIME_CACHE_ONLINE = "public, max-age = "// 1 minute
        private val TIME_CACHE_OFFLINE = "public, only-if-cached, max-stale = "

        const val HEADER_DEVICE_ID = "DeviceId"
        const val HEADER_DEVICE_NAME = "DeviceName"
        const val HEADER_LANGUAGE_ID = "LanguageId"
        const val HEADER_TOKEN = "Authorization"
        //        private var BASE_URL = "http://apif49.itpsolution.net/"
//        private var BASE_URL = "http://api.f49.vn/"
        private const val CONNECT_TIMEOUT: Long = 20000
        private const val READ_TIMEOUT: Long = 20000
        private var retrofit: Retrofit? = null

//        private var builder: Retrofit.Builder = Retrofit.Builder().baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

        private val okHttpClientBuilder = UnsafeOkHttpClient.unsafeOkHttpClientBuilder
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)

        fun <S> createService(context: Context, serviceClass: Class<S>): S {
            return createService(context, serviceClass, null)
        }

        private fun <S> createService(context: Context, serviceClass: Class<S>, authToken: Map<String, String>?): S {
            if (retrofit == null) {

                okHttpClientBuilder.addInterceptor(getRequestInterceptor(context))
                if (BuildConfig.DEBUG) {
                    val logging = HttpLoggingInterceptor()
                    logging.level = HttpLoggingInterceptor.Level.BODY
                    okHttpClientBuilder.addInterceptor(logging)
                }
                val httpClient = okHttpClientBuilder.build()

                if (authToken != null) {
                    var interceptor = AuthenticationInterceptor(authToken)
                    if (!httpClient.interceptors().contains(interceptor)) {
                        okHttpClientBuilder.addInterceptor(interceptor)

                    }
                }

                val gson = GsonBuilder()
                    .registerTypeAdapter(Date::class.java, DateTypeDeserializer())
                    .create()

                retrofit = Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(
                        GsonConverterFactory.create(gson)
                    )
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(httpClient)
                    .build()
            }
            return retrofit!!.create(serviceClass)
        }

//        private fun getRequestInterceptor(context: Context): Interceptor {
//            return Interceptor { chain ->
//                val original = chain.request()
//
//                val request = original.newBuilder()
//                    .header(HEADER_DEVICE_ID, GeneralUtils.getDeviceId(context))
//                    .header(HEADER_DEVICE_NAME, GeneralUtils.getDeviceName())
//                    .header(HEADER_LANGUAGE_ID, PreferenceUtils.getString(context, PreferenceUtils.KEY_LANGUAGE_ID, Constants.EMPTY_STRING))
//                    .header(HEADER_TOKEN, PreferenceUtils.getString(context, PreferenceUtils.KEY_TOKEN, Constants.EMPTY_STRING))
//                    .method(original.method(), original.body())
//                    .build()
//
//                chain.proceed(request)
//            }
//        }

        private fun getRequestInterceptor(context: Context): Interceptor {
            return Interceptor { chain ->
                val original = chain.request()
                if (hasNetwork(context)) {
                    val request = original.newBuilder()
                        .header(CACHE_CONTROL, TIME_CACHE_ONLINE+60)
                        .header(HEADER_DEVICE_ID, GeneralUtils.getDeviceId(context) ?: "")
                        .header(HEADER_DEVICE_NAME, GeneralUtils.getDeviceName() ?: "")
                        .header(
                            HEADER_LANGUAGE_ID,
                            PreferenceUtils.getString(
                                context,
                                PreferenceUtils.KEY_LANGUAGE_ID,
                                Constants.EMPTY_STRING
                            ) ?: ""
                        )
                        .header(
                            HEADER_TOKEN,
                            PreferenceUtils.getString(
                                context,
                                PreferenceUtils.KEY_TOKEN,
                                Constants.EMPTY_STRING
                            ) ?: ""
                        )
                        .method(original.method(), original.body())
                        .build()

                    chain.proceed(request)
                }else{
                    val request = original.newBuilder()
                        .header(CACHE_CONTROL, TIME_CACHE_OFFLINE+86400)
                        .header(HEADER_DEVICE_ID, GeneralUtils.getDeviceId(context) ?: "")
                        .header(HEADER_DEVICE_NAME, GeneralUtils.getDeviceName() ?: "")
                        .header(
                            HEADER_LANGUAGE_ID,
                            PreferenceUtils.getString(
                                context,
                                PreferenceUtils.KEY_LANGUAGE_ID,
                                Constants.EMPTY_STRING
                            ) ?: ""
                        )
                        .header(
                            HEADER_TOKEN,
                            PreferenceUtils.getString(
                                context,
                                PreferenceUtils.KEY_TOKEN,
                                Constants.EMPTY_STRING
                            ) ?: ""
                        )
                        .method(original.method(), original.body())
                        .build()

                    chain.proceed(request)
                }

            }
        }

        fun hasNetwork(context: Context): Boolean {
            var isConnected: Boolean? = false // Initial Value
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
            if (activeNetwork != null && activeNetwork.isConnected)
                isConnected = true
            return isConnected ?: false
        }

    }

    class AuthenticationInterceptor(private val authToken: Map<String, String>) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val original = chain.request()
            val builder = original.newBuilder()
            for (key in authToken.keys) {
                builder.header(key, authToken.getValue(key))
            }
            val request = builder.build()
            return chain.proceed(request)
        }
    }
}