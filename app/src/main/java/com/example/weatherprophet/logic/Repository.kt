package com.example.weatherprophet.logic

import androidx.lifecycle.liveData
import com.example.weatherprophet.logic.model.Place
import com.example.weatherprophet.logic.network.WeatherProphetNetwork
import kotlinx.coroutines.Dispatchers
import retrofit2.http.Query
import java.lang.Exception
import java.lang.RuntimeException

object Repository {
    //liveData()自动构建并返回一个livedata对象，其代码块中提供一个挂起函数的上下文
    fun searchPlaces(query: String) = liveData(Dispatchers.IO) {
        val result = try {
            val placeResponse = WeatherProphetNetwork.searchPlaces(query)
            if (placeResponse.status == "ok") {
                val places = placeResponse.places
                Result.success(places)
            } else {
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
        } catch (e: Exception) {
            Result.failure<List<Place>>(e)
        }
        emit(result)
    }
}