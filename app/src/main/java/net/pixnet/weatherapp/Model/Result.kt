package net.pixnet.weatherapp.Model


import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("fields")
    val fields: List<Field>,
    @SerializedName("resource_id")
    val resourceId: String
)