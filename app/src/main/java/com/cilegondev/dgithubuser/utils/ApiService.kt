package com.cilegondev.dgithubuser.utils

import com.cilegondev.dgithubuser.models.SearchUserResponse
import com.cilegondev.dgithubuser.models.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    fun getAllUsers() : Call<ArrayList<User>>

    @GET("search/users")
    fun searchUsers(@Query("q") q: String) : Call<SearchUserResponse>

    @GET("users/{name}")
    fun getUser(@Path("name") name: String) : Call<User>

    @GET("users/{name}/followers")
    fun getFollowers(@Path("name") name: String) : Call<ArrayList<User>>

    @GET("users/{name}/following")
    fun getFollowing(@Path("name") name: String) : Call<ArrayList<User>>
}