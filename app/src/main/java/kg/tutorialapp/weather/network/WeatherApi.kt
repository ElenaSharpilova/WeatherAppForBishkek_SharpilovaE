package kg.tutorialapp.weather.network

import kg.tutorialapp.weather.models.ForeCast
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("onecall?lat=42.882004&lon=74.582748&exclude=minutely&appid=57e7f5c7bbdfd9025acb0a425a2c113b&lang=ru&units=metric")
    fun fetchWeather() : Call<ForeCast>

    @GET("onecall")
    fun fetWeatherUsingQuerry(
            @Query("lat") lat: Double = 42.882004,
            @Query("lon") lon: Double = 74.582748,
            @Query("exclude") exclude: String = "minutely",
            @Query("appid") appid: String = "57e7f5c7bbdfd9025acb0a425a2c113b",
            @Query("lang") lang: String = "ru",
            @Query("units") units: String = "metric"
    ) : Call<ForeCast>
}