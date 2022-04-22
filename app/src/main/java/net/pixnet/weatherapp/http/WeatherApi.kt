package net.pixnet.weatherapp.http


import io.reactivex.rxjava3.core.Observable
import net.pixnet.weatherapp.Model.WeatherModel
import retrofit2.http.*

interface WeatherApi {
    /*取得縣市天氣API*/
    @GET("api/v1/rest/datastore/F-C0032-001")
    fun getCity(
        @Query(value = "locationName") cityName: String?,
        @Query(value = "elementName") elementName: String?
    ): Observable<WeatherModel>

}