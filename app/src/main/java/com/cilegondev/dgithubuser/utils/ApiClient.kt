package com.cilegondev.dgithubuser.utils

import com.cilegondev.dgithubuser.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    companion object {
        private const val baseUrl = "https://api.github.com/"
        private val httpClient = OkHttpClient.Builder()
        fun create(): ApiService {
            httpClient.addInterceptor(Interceptor {
                val original = it.request()
                val request = original.newBuilder()
                    .header("User-Agent", "request")
                    .header("Authorization", BuildConfig.API_KEY)
                    .method(original.method, original.body)
                    .build()
                it.proceed(request)

            })
            val client = httpClient.build()
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl(baseUrl)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}