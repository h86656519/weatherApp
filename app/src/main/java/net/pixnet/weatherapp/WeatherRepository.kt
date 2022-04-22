package net.pixnet.weatherapp

import net.pixnet.weatherapp.http.ApiManager
import net.pixnet.weatherapp.http.WeatherApi

class WeatherRepository {
    private val apiManager: ApiManager = ApiManager.getInstance()

    fun fetchRemoteWeather(): WeatherApi {
        return apiManager.getWeatherApiRetrofit()
    }
}