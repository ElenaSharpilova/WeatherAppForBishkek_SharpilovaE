package kg.tutorialapp.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kg.tutorialapp.weather.models.ForeCast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    lateinit var textView: TextView
    lateinit var textView2: TextView
    lateinit var tv_counter: TextView
    lateinit var btn_start_work: Button
    lateinit var btn_show_toast: Button
    private var workResult = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        fetchWeatherUsingQuerry()
        setup()
    }

    private fun setup() {
        btn_start_work = findViewById(R.id.btn_start_work)
        btn_start_work.setOnClickListener {
            doSomeWork()
        }

        btn_show_toast = findViewById(R.id.btn_show_toast)
        btn_show_toast.setOnClickListener {
            Toast.makeText(this, "Hello", Toast.LENGTH_LONG).show()
        }
    }

    private fun doSomeWork(){
        Thread(Runnable {
            for(i in 0..4){
                Thread.sleep(1000)
                workResult++
            }

            runOnUiThread {
                tv_counter = findViewById(R.id.tv_counter)
                tv_counter.text = workResult.toString()
            }

//            Handler(Looper.getMainLooper()).post(Runnable {
//                tv_counter = findViewById(R.id.tv_counter)
//                tv_counter.text = workResult.toString()
//            })


        }).start()

    }

    private fun fetchWeatherUsingQuerry() {
        val call = WeatherClient.weatherApi.fetWeatherUsingQuerry(lat = 40.513996, lon = 72.816101, lang = "en")

        call.enqueue(object : Callback<ForeCast> {
            override fun onFailure(call: Call<ForeCast>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<ForeCast>, response: Response<ForeCast>) {
                if (response.isSuccessful) {
                    val foreCast = response.body()
                    foreCast?.let {
                        textView = findViewById(R.id.textView)
                        textView2 = findViewById(R.id.textView2)
                        textView.text = it.current?.weather!![0].description
                        textView2.text = it.current?.temp?.toString()

                    }
                }
            }

        })

    }
}
