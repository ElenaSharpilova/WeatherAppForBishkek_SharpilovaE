package kg.tutorialapp.weather.ui.rv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kg.tutorialapp.weather.R
import kg.tutorialapp.weather.format
import kg.tutorialapp.weather.models.Constants
import kg.tutorialapp.weather.models.HourlyForeCast
import kotlin.math.roundToInt

class HourlyForeCastVH(itemView: View): RecyclerView.ViewHolder(itemView) {
    lateinit var tv_time: TextView
    lateinit var tv_precipitation: TextView
    lateinit var tv_temp: TextView
    lateinit var iv_weather_icon: ImageView

    fun bind(item: HourlyForeCast) {
        itemView.run {
            tv_time = findViewById(R.id.tv_time)
            tv_time.text = item.date?.format("HH:mm")

            tv_precipitation = findViewById(R.id.tv_precipitation)
            item.probability?.let {
                tv_precipitation.text = "${(it * 100).roundToInt()}%"
            }

            tv_temp = findViewById(R.id.tv_temp)
            tv_temp.text = item.temp?.roundToInt()?.toString()

            iv_weather_icon = findViewById(R.id.iv_weather_icon)
            Glide.with(itemView.context)
                .load("${Constants.iconUri}${item.weather?.get(0)?.icon}${Constants.iconFormat}")
                .into(iv_weather_icon)
        }
    }

    companion object{
        fun create(parent: ViewGroup): HourlyForeCastVH {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_hourly_forecast, parent, false)
            return  HourlyForeCastVH(view)
        }
    }
}