package com.cilegondev.dgithubuser.models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cilegondev.dgithubuser.utils.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailViewModel: ViewModel() {
    val user = MutableLiveData<User>()
    val followers = MutableLiveData<ArrayList<User>>()
    val followings = MutableLiveData<ArrayList<User>>()

    fun setUser(login_name: String) {
        ApiClient.create().getUser(login_name).enqueue(object : Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d("ERROR ", t.message.toString())
            }
            override fun onResponse(
                call: Call<User>,
                response: Response<User>
            ) {
                user.postValue(response.body())
                setFollowers(login_name)
                setFollowings(login_name)
            }
        })
    }

    fun setFollowers(login_name: String) {
        ApiClient.create().getFollowers(login_name).enqueue(object : Callback<ArrayList<User>> {
            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                Log.d("ERROR ", t.message.toString())
            }
            override fun onResponse(
                call: Call<ArrayList<User>>,
                response: Response<ArrayList<User>>
            ) {
                followers.postValue(response.body())
            }
        })
    }

    fun setFollowings(login_name: String) {
        ApiClient.create().getFollowing(login_name).enqueue(object : Callback<ArrayList<User>> {
            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                Log.d("ERROR ", t.message.toString())
            }
            override fun onResponse(
                call: Call<ArrayList<User>>,
                response: Response<ArrayList<User>>
            ) {
                followings.postValue(response.body())
            }
        })
    }

    fun getUser(): LiveData<User> {
        return user
    }

    fun getFollowers(): LiveData<ArrayList<User>>{
        return followers
    }

    fun getFollowings(): LiveData<ArrayList<User>>{
        return followings
    }
}