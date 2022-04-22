package net.pixnet.weatherapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.pixnet.weatherapp.Model.Time
import net.pixnet.weatherapp.databinding.PictureLayoutBinding
import net.pixnet.weatherapp.databinding.WeatherInfoLayoutBinding


class WeatherAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var weatherList = listOf<Time>()
    private val weatherDataViewType = 0   //weather data
    private val pictureViewType = 1 //picture
    private var mOnItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val weatherBinding = WeatherInfoLayoutBinding.inflate(layoutInflater, parent, false)
        val pictureBinding = PictureLayoutBinding.inflate(layoutInflater, parent, false)

        return when (viewType) {
            weatherDataViewType -> WeatherViewHolder(weatherBinding)
            pictureViewType -> PictureViewHolder(pictureBinding)
            else -> PictureViewHolder(pictureBinding)
        }
    }


    override fun getItemViewType(position: Int): Int {
        super.getItemViewType(position)
        return when (position % 2) {
            0 -> weatherDataViewType
            1 -> pictureViewType
            else -> {
                1
            }
        }
    }

    override fun getItemCount() = weatherList.size * 2

    fun updateList(list: List<Time>) {
        weatherList = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == weatherDataViewType) {

            val weatherHolder = holder as WeatherViewHolder
            weatherHolder.updateWeatherItem()

            weatherHolder.setStartTimeOnClickListener()
        }
    }

    inner class WeatherViewHolder(private val binding: WeatherInfoLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun updateWeatherItem() {
            binding.tvStartTime.text = weatherList[position / 2].startTime
            binding.tvEndTime.text = weatherList[position / 2].endTime
            binding.tvParameterName.text = weatherList[position / 2].parameter.parameterName
            binding.tvParameterUnit.text = weatherList[position / 2].parameter.parameterUnit
        }

        fun setStartTimeOnClickListener(){
            binding.root.setOnClickListener{
                mOnItemClickListener!!.onItemClick(weatherList[position / 2])
            }
        }
    }

    inner class PictureViewHolder(itemView: PictureLayoutBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val normalHolderPlanName = itemView.ivPlaceHolder
    }

    interface OnItemClickListener {
        fun onItemClick(time: Time)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.mOnItemClickListener = listener
    }
}