package net.pixnet.weatherapp.http

import android.content.Context
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*curl -X GET "https://opendata.cwb.gov.tw/api/v1/rest/datastore/F-C0032-001
?Authorization=CWB-3F106D7A-6907-4463-AA2E-A94FED75890E
&locationName=%E8%87%BA%E5%8C%97%E5%B8%82" -H  "accept: application/json"*/
class ApiManager() {
    private var retrofit: Retrofit
    private var accessToken = "CWB-3F106D7A-6907-4463-AA2E-A94FED75890E"

    companion object {
        private lateinit var okHttpClient: OkHttpClient.Builder
        private var apiManager: ApiManager? = null

        @JvmStatic
        fun getInstance(): ApiManager {
            return ApiManager().also { apiManager = it }
        }
    }

    init {
        okHttpClient = OkHttpClient.Builder()

        okHttpClient.addInterceptor(Interceptor { chain: Interceptor.Chain ->
            val builder: Request.Builder = chain.request().newBuilder()

            builder.addHeader("Authorization", accessToken)
            val originRequest: Request = builder.build()
            chain.proceed(originRequest)
        }).addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })

        retrofit = Retrofit.Builder()
            .baseUrl("https://opendata.cwb.gov.tw/")
            .client(okHttpClient.build())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getWeatherApiRetrofit(): WeatherApi {
        return retrofit.create(WeatherApi::class.java)
    }
}