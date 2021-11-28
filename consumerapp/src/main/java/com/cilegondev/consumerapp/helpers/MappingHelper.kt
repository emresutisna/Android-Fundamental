package com.cilegondev.consumerapp.helpers

import android.database.Cursor
import com.cilegondev.consumerapp.db.DatabaseContract
import com.cilegondev.consumerapp.models.User

object MappingHelper {
    fun mapCursorToArrayList(usersCursor: Cursor?): ArrayList<User> {
        val usersList = ArrayList<User>()
        usersCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.UserColumns._ID))
                val name = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.NAME))
                val avatar_url =
                    getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.AVATAR_URL))
                val company = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.COMPANY))
                val followers =
                    getInt(getColumnIndexOrThrow(DatabaseContract.UserColumns.FOLLOWERS))
                val following =
                    getInt(getColumnIndexOrThrow(DatabaseContract.UserColumns.FOLLOWING))
                val location =
                    getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.LOCATION))
                val public_repos =
                    getInt(getColumnIndexOrThrow(DatabaseContract.UserColumns.PUBLIC_REPOS))
                val login = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.LOGIN))
                val url = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.URL))
                usersList.add(
                    User(
                        id,
                        login,
                        avatar_url,
                        url,
                        name,
                        company,
                        location,
                        followers,
                        following,
                        public_repos
                    )
                )
            }
        }
        return usersList
    }

    fun mapCursorToObject(userCursor: Cursor?): User {
        var user = User()
        userCursor?.apply {
            moveToFirst()
            val id = getInt(getColumnIndexOrThrow(DatabaseContract.UserColumns._ID))
            val name = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.NAME))
            val avatar_url =
                getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.AVATAR_URL))
            val company = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.COMPANY))
            val followers = getInt(getColumnIndexOrThrow(DatabaseContract.UserColumns.FOLLOWERS))
            val following = getInt(getColumnIndexOrThrow(DatabaseContract.UserColumns.FOLLOWING))
            val location = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.LOCATION))
            val public_repos =
                getInt(getColumnIndexOrThrow(DatabaseContract.UserColumns.PUBLIC_REPOS))
            val login = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.LOGIN))
            val url = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.URL))
            user = User(
                id,
                login,
                avatar_url,
                url,
                name,
                company,
                location,
                followers,
                following,
                public_repos
            )
        }
        return user
    }
}