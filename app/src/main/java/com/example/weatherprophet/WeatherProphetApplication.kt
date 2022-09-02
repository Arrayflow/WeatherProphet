package com.example.weatherprophet

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class WeatherProphetApplication : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        const val TOKEN = "ijA8NlbWaxdzQd8i"
    }

    override fun onCreate() {
        super.onCreate()
        context = this.applicationContext
    }
}