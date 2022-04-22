package net.pixnet.weatherapp.Model


import com.google.gson.annotations.SerializedName

data class Records(
    @SerializedName("datasetDescription")
    val datasetDescription: String,
    @SerializedName("location")
    val location: List<Location>
)