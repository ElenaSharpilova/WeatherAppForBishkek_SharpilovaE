package kg.tutorialapp.weather.di


import android.content.Context
import androidx.room.Room
import kg.tutorialapp.weather.network.WeatherApi
import kg.tutorialapp.weather.repo.WeatherRepo
import kg.tutorialapp.weather.storage.ForeCastDatabase
import kg.tutorialapp.weather.ui.MainViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val vmModule = module {
    viewModel { MainViewModel(get()) }
}

val dataModule = module {
    single { provideForeCastDatabase(androidApplication()) }
    single { provideHttpClint() }
    single { provideRetrofit(get()) }
    factory { provideWeatherApi(get()) }
    factory { WeatherRepo(get(), get()) }
}

fun provideForeCastDatabase(context: Context) =
    Room.databaseBuilder(
        context,
        ForeCastDatabase::class.java,
        ForeCastDatabase.DB_NAME
    )
        .fallbackToDestructiveMigration()
        .build()

private fun provideHttpClint(): OkHttpClient {
    val interceptor = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    return OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()
}

private fun provideRetrofit(httpClient: OkHttpClient) =
    Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/data/2.5/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(httpClient)
        .build()

private fun provideWeatherApi(retrofit: Retrofit) = retrofit.create(WeatherApi::class.java)