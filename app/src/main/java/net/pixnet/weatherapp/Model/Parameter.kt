package net.pixnet.weatherapp.Model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Parameter(
    @SerializedName("parameterName")
    val parameterName: String,
    @SerializedName("parameterUnit")
    val parameterUnit: String,
    @SerializedName("parameterValue")
    val parameterValue: String
) : Parcelable