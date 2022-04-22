package net.pixnet.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.pixnet.weatherapp.Model.Time
import net.pixnet.weatherapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(application,WeatherRepository())
        ).get(MainViewModel::class.java)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding!!.root)

        viewModel.getWeatherDate()

        initView()

        val enterAppTimes = getEnterAppTime(0) + 1
        viewModel.checkEnterAppTimes(enterAppTimes)
        recordEnterAppTime(enterAppTimes)

        viewModel.getToastLiveData().observe(this) {
            showToast(it)
        }
    }

    private fun initView() {
        val linearLayoutManager = LinearLayoutManager(
            this,
            RecyclerView.VERTICAL, false
        )

        binding!!.rvWeather.layoutManager = linearLayoutManager
        val adapter = WeatherAdapter()
        binding!!.rvWeather.adapter = adapter

        viewModel.getWeatherLiveData().observe(this) {
            adapter.updateList(it.records.location[0].weatherElement[0].time)
        }

        adapter.setOnItemClickListener(object : WeatherAdapter.OnItemClickListener {
            override fun onItemClick(time: Time) {
                gotToWeatherDetail(time)
            }
        })
    }

    fun gotToWeatherDetail(timeData: Time) {
        addFragment(WeatherDetailFragment.newInstance(timeData), "WeatherDetailFragment")
    }

    private fun addFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
            .add(R.id.content_fragment, fragment, tag)
            .addToBackStack(fragment.javaClass.name).commit()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()

    }

    /*進入app 次數*/
    private fun recordEnterAppTime(value: Int) {
        val sp = PreferenceManager.getDefaultSharedPreferences(this)
        sp.edit().putInt("enter_app_times", value).apply()
    }

    private fun getEnterAppTime(defValue: Int): Int {
        val sp = PreferenceManager.getDefaultSharedPreferences(this)
        return sp.getInt("enter_app_times", defValue)
    }

}