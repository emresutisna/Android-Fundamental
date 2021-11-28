package com.cilegondev.dgithubuser.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchUserResponse(
    @SerializedName("total_cont")
    var total_count: Int = 0,
    @SerializedName("incomplete_results")
    var incomplete_results: Boolean = false,
    @SerializedName("items")
    var items: ArrayList<User> = ArrayList()
): Parcelable