package com.example.weatherprophet.logic.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object WeatherProphetNetwork {
    //ServiceCreator创建一个PlaceService的动态代理对象
    private val placeService = ServiceCreator.create<PlaceService>()
    //调用searchPlaces()方法，发起城市搜索的请求
    suspend fun searchPlaces(query: String) = placeService.searchPlaces(query).await()


    /**************** 定义await函数:***********
     * suspendCoroutine():必须在协程作用域或挂起函数中使用，接收一个Lambda表达式的参数，其中的参数列表为-
     * Continuation参数，并通过调用其resume()或resumeWithException()方法让协程恢复执行
     *
     * suspendCoroutine函数将当前协程立即挂起，然后在一个普通的线程中执行Lambda中的表达式
     * @receiver Call<T>
     * @return T
     */
    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            //外部调用WeatherProphet的searchPlaces()函数时，由Retrofit立即发起网络请求并阻塞协程，当服务器响应之后
            //await()将结果将解析后的数据模型对象()取出并返回，同时恢复当前协程。
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(
                        RuntimeException("response body is null"))
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

            })
        }
    }
}