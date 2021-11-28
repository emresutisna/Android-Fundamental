package com.cilegondev.dgithubuser.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User (
    @SerializedName("login")
    var login: String?,
    @SerializedName("avatar_url")
    var avatar_url: String?,
    @SerializedName("url")
    var url : String?,
    @SerializedName("name")
    var name : String?,
    @SerializedName("company")
    var company: String?,
    @SerializedName("location")
    var location: String?,
    @SerializedName("followers")
    var followers: Int = 0,
    @SerializedName("following")
    var following: Int = 0,
    @SerializedName("public_repos")
    var public_repos: Int = 0
): Parcelable