package net.pixnet.weatherapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import net.pixnet.weatherapp.Model.Time
import net.pixnet.weatherapp.databinding.FragmentWeatherDetailBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class WeatherDetailFragment : Fragment() {

    private var paramTime: Time? = null
    private lateinit var binding: FragmentWeatherDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { paramTime = it.getParcelable(ARG_PARAM1) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherDetailBinding.inflate(layoutInflater)
        val weatherString =
            "${paramTime?.startTime} \n " +
                    "${paramTime?.endTime} \n " +
                    "${paramTime?.parameter?.parameterName} ${paramTime?.parameter?.parameterUnit}"

        binding.tvWeatherInfo.text = weatherString
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(time: Time) =
            WeatherDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PARAM1, time)
                }
            }
    }
}