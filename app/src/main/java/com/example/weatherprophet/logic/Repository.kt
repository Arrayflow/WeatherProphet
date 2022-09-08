package com.example.weatherprophet.logic

import androidx.lifecycle.liveData
import com.example.weatherprophet.logic.model.Place
import com.example.weatherprophet.logic.model.Weather
import com.example.weatherprophet.logic.network.WeatherProphetNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import retrofit2.http.Query
import java.lang.Exception
import java.lang.RuntimeException
import kotlin.coroutines.CoroutineContext

object Repository {

    //liveData()自动构建并返回一个livedata对象，其代码块中提供一个挂起函数的上下文
    fun searchPlaces(query: String) = fire(Dispatchers.IO) {
        val placeResponse = WeatherProphetNetwork.searchPlaces(query)
        if (placeResponse.status == "ok") {
            val places = placeResponse.places
            Result.success(places)
        } else {
            Result.failure(RuntimeException("response status is ${placeResponse.status}"))
        }

    }

    fun refreshWeather(lng: String, lat: String) = fire(Dispatchers.IO) {
        coroutineScope {
            val deferredRealtime = async {
                WeatherProphetNetwork.getRealtimeWeather(lng, lat)
            }
            val deferredDaily = async {
                WeatherProphetNetwork.getDailyWeather(lng, lat)
            }
            val realtimeResponse = deferredRealtime.await()
            val dailyResponse = deferredDaily.await()
            if (realtimeResponse.status == "ok" && dailyResponse.status == "ok") {
                val weather = Weather(realtimeResponse.result.realtime, dailyResponse.result.daily)
                Result.success(weather)
            } else {
                Result.failure(
                    RuntimeException("realtime response status is ${realtimeResponse.status}" +
                        "daily response status is ${dailyResponse.status}")
                )
            }
        }

    }

    /**
     * ************* fire()函数 *****************
     *     fire()函数接收的是按照liveData()的标准，首先在函数内部调用liveData,并在其参数范围内
     * 设置了try-catch
     *
     * @param context CoroutineContext
     * @param block SuspendFunction0<Result<T>>
     * @return LiveData<Result<T>>
     */
    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }

}