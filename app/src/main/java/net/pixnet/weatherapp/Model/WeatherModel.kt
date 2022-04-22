package net.pixnet.weatherapp.Model


import com.google.gson.annotations.SerializedName

data class WeatherModel(
    @SerializedName("records")
    val records: Records,
    @SerializedName("result")
    val result: Result,
    @SerializedName("success")
    val success: String
)