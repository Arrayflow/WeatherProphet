package com.example.weatherprophet.logic.dao

import android.content.Context
import androidx.core.content.edit
import com.example.weatherprophet.WeatherProphetApplication
import com.example.weatherprophet.logic.model.Place
import com.google.gson.Gson

object PlaceDao {

    fun savePlace(place: Place) {
        sharedPreferences().edit {
            putString("place", Gson().toJson(place)) //先通过GSON将Place对象转为Json字符串，然后通过sp保存
        }
    }

    fun getSavedPlace(): Place {
        //读取sp时，先读取sp，然后通过Gson将Json字符串解析为Place对象
        val placeJson = sharedPreferences().getString("place", "")
        return Gson().fromJson(placeJson, Place::class.java)
    }

    fun isPlaceSaved() = sharedPreferences().contains("place")

    private fun sharedPreferences() = WeatherProphetApplication.context
        .getSharedPreferences("weather_prophet", Context.MODE_PRIVATE)
}