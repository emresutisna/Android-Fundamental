package com.cilegondev.dgithubuser.models

import android.content.ContentValues
import android.os.Parcelable
import androidx.core.content.contentValuesOf
import com.cilegondev.dgithubuser.db.DatabaseContract
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("login")
    var login: String? = null,
    @SerializedName("avatar_url")
    var avatar_url: String? = null,
    @SerializedName("url")
    var url: String? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("company")
    var company: String? = null,
    @SerializedName("location")
    var location: String? = null,
    @SerializedName("followers")
    var followers: Int = 0,
    @SerializedName("following")
    var following: Int = 0,
    @SerializedName("public_repos")
    var public_repos: Int = 0
) : Parcelable {
    fun convertToContentValues(): ContentValues {
        return contentValuesOf(
            DatabaseContract.UserColumns._ID to this.id,
            DatabaseContract.UserColumns.LOGIN to this.login,
            DatabaseContract.UserColumns.AVATAR_URL to this.avatar_url,
            DatabaseContract.UserColumns.COMPANY to this.company,
            DatabaseContract.UserColumns.FOLLOWERS to this.followers,
            DatabaseContract.UserColumns.FOLLOWING to this.following,
            DatabaseContract.UserColumns.LOCATION to this.location,
            DatabaseContract.UserColumns.NAME to this.name,
            DatabaseContract.UserColumns.PUBLIC_REPOS to this.public_repos,
            DatabaseContract.UserColumns.URL to this.url
        )
    }
}