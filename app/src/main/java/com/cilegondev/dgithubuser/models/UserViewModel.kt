package com.cilegondev.dgithubuser.models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cilegondev.dgithubuser.utils.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel: ViewModel() {
    val listUsers = MutableLiveData<ArrayList<User>>()

    fun setUser(name: String) {
        if (name == "") {
            ApiClient.create().getAllUsers().enqueue(object : Callback<ArrayList<User>> {
                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    Log.d("ERROR ", t.message.toString())
                }
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    listUsers.postValue(response.body())
                    Log.d("RESPONSE : ", response.body().toString())
                }

            })
        }else{
            ApiClient.create().searchUsers(name).enqueue(object : Callback<SearchUserResponse> {
                override fun onFailure(call: Call<SearchUserResponse>, t: Throwable) {
                    Log.d("ERROR ", t.message.toString())
                }
                override fun onResponse(
                    call: Call<SearchUserResponse>,
                    response: Response<SearchUserResponse>
                ) {
                    listUsers.postValue(response.body()?.items)
                    Log.d("RESPONSE : ", response.body().toString())
                }

            })
        }
    }
    fun getUsers(): LiveData<ArrayList<User>> {
        return listUsers
    }
}