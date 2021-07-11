package kg.tutorialapp.weather.network

import io.reactivex.Single
import kg.tutorialapp.weather.models.ForeCast
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("onecall")
    fun fetchWeather(
            @Query("lat") lat: Double = 42.8746,
            @Query("lon") lon: Double = 74.5698,
            @Query("exclude") exclude: String = "minutely",
            @Query("appid") appid: String = "57e7f5c7bbdfd9025acb0a425a2c113b",
            @Query("lang") lang: String = "ru",
            @Query("units") units: String = "metric"

    ) : Single<ForeCast>
}