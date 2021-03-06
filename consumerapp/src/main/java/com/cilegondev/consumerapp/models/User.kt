package com.cilegondev.consumerapp.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var id: Int = 0,
    var login: String? = null,
    var avatar_url: String? = null,
    var url: String? = null,
    var name: String? = null,
    var company: String? = null,
    var location: String? = null,
    var followers: Int = 0,
    var following: Int = 0,
    var public_repos: Int = 0
) : Parcelable