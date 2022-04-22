package net.pixnet.weatherapp.Model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Time (
    @SerializedName("endTime")
    val endTime: String,
    @SerializedName("parameter")
    val parameter: Parameter,
    @SerializedName("startTime")
    val startTime: String
) : Parcelable