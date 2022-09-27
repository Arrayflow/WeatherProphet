package com.example.weatherprophet.logic.network

import com.example.weatherprophet.WeatherProphetApplication
import com.example.weatherprophet.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceService {
    //用GET注解表示，当调用下述方法的时候，Retrofit会自动发起一条GET请求，访问注解中配置的地址
    @GET("v2/place?token=${WeatherProphetApplication.TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query: String): Call<PlaceResponse>
    //query参数需要动态指定，因此采用@Query注解
    //Call<>用于将服务器返回的json自动解析为PlaceResponse对象
}