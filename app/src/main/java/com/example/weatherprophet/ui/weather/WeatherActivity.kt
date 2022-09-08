package com.example.weatherprophet.ui.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weatherprophet.R
import com.example.weatherprophet.databinding.ActivityWeatherBinding

class WeatherActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWeatherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

    }
}