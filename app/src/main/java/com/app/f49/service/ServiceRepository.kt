package vn.com.ttc.ecommerce.service

import android.content.Context
import com.app.f49.BuildConfig
import com.app.f49.utils.Constants
import com.app.f49.utils.GeneralUtils
import com.app.f49.utils.PreferenceUtils
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ServiceRepository {
    companion object {
        const val HEADER_DEVICE_ID = "DeviceId"
        const val HEADER_DEVICE_NAME = "DeviceName"
        const val HEADER_LANGUAGE_ID = "LanguageId"
        const val HEADER_TOKEN = "Token"
        private var BASE_URL = "http://ecom.ttcgroup.vn/api/"
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

                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(
                        GsonConverterFactory.create(
//                            GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
                        )
                    )
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(httpClient)
                    .build()
            }
            return retrofit!!.create(serviceClass)
        }

        private fun getRequestInterceptor(context: Context): Interceptor {
            return Interceptor { chain ->
                val original = chain.request()

                val request = original.newBuilder()
                    .header(HEADER_DEVICE_ID, GeneralUtils.getDeviceId(context))
                    .header(HEADER_DEVICE_NAME, GeneralUtils.getDeviceName())
                    .header(
                        HEADER_LANGUAGE_ID,
                        PreferenceUtils.getString(context, PreferenceUtils.KEY_LANGUAGE_ID, Constants.EMPTY_STRING)
                    )
                    .header(
                        HEADER_TOKEN,
                        PreferenceUtils.getString(context, PreferenceUtils.KEY_TOKEN, Constants.EMPTY_STRING)
                    )
                    .method(original.method(), original.body())
                    .build()

                chain.proceed(request)
            }
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