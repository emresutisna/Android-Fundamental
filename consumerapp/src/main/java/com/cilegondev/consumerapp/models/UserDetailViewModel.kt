package com.cilegondev.consumerapp.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserDetailViewModel : ViewModel() {
    val mUser = MutableLiveData<User>()

    fun setUser(user: User) {
        mUser.postValue(user)
    }

    fun getUser(): LiveData<User> {
        return mUser
    }
}