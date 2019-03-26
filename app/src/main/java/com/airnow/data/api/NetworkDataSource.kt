package com.airnow.data.api

import com.airnow.BuildConfig
import com.airnow.data.DataStatus
import com.airnow.data.model.Forecast
import com.airnow.data.model.Weather
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


class NetworkDataSource(private val networkSystem: NetworkSystemAbstract) : INetworkDataSource(networkSystem) {


    private fun initRetrofitOpenWateherAPI(): OpenWeatherAPI {
        val retrofit = Retrofit.Builder().apply {
            baseUrl(BuildConfig.OPEN_WEATHER_API_URL_BASE)
            client(okHttpClient)
            addConverterFactory(GsonConverterFactory.create())
            addCallAdapterFactory(CoroutineCallAdapterFactory())
        }.build()

        val openWeatherAPI = retrofit.create(OpenWeatherAPI::class.java)
        return openWeatherAPI
    }

    override suspend fun getCurrentWeatherByName(cityName:String): DataStatus<Weather> {
        val openWeatherAPI = initRetrofitOpenWateherAPI()
        try {
            val currentWeather =
                openWeatherAPI.currentWeatherByName(cityName)
                    .await()

            return DataStatus.Successful(currentWeather)
        } catch (e: Exception) {
            return DataStatus.Failed(e)
        }
    }

    override suspend fun getForecast(cityName: String): DataStatus<Forecast> {
        val openWeatherAPI = initRetrofitOpenWateherAPI()
        try {
            val forecast =
                openWeatherAPI.forecastByName(cityName)
                    .await()

            return DataStatus.Successful(forecast)
        } catch (e: Exception) {
            return DataStatus.Failed(e)
        }
    }

    var okHttpClient = OkHttpClient.Builder()
        .addInterceptor(object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response {
                val request = chain.request()
                val response = chain.proceed(request)

                if (response.code() == 500) {
                    return response
                }

                return response
            }
        })
        .build()

}