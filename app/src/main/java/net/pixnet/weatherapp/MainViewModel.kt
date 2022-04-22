package net.pixnet.weatherapp

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import net.pixnet.weatherapp.Model.WeatherModel

class MainViewModel(application: Application, private val repo: WeatherRepository) :
    AndroidViewModel(application) {
    private val compositeDisposable = CompositeDisposable()
    private val weatherLiveData = MutableLiveData<WeatherModel>()
    private val toastLiveData = MutableLiveData<String>()


    fun getWeatherDate() {
        val weatherDisposable =
            repo.fetchRemoteWeather().getCity("臺北市", "MinT")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally { println("取得天氣資料完成") }
                .subscribe({ model: WeatherModel ->
                    println("資料回來了")
                    weatherLiveData.value = model
                }) { e: Throwable? ->
                    println("天氣資料取得失敗")
                }
        compositeDisposable.add(weatherDisposable)
    }

    fun checkEnterAppTimes(enterTimes: Int) {
        if (enterTimes == 2) {
            toastLiveData.value = getApplication<Application?>().getString(R.string.wellcome_text)
        }
    }

    fun getWeatherLiveData(): MutableLiveData<WeatherModel> {
        return weatherLiveData
    }

    fun getToastLiveData(): MutableLiveData<String> {
        return toastLiveData
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}