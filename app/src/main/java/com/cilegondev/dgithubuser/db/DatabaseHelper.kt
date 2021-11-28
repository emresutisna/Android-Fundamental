package com.cilegondev.dgithubuser.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.cilegondev.dgithubuser.db.DatabaseContract.UserColumns
import com.cilegondev.dgithubuser.db.DatabaseContract.UserColumns.Companion.TABLE_NAME

internal class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "dbdgithubuser"
        private const val DATABASE_VERSION = 1
        private val SQL_CREATE_TABLE_USER = """
            CREATE TABLE $TABLE_NAME 
                (
                ${UserColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT,
                ${UserColumns.AVATAR_URL} TEXT NOT NULL,
                ${UserColumns.LOGIN} TEXT NOT NULL,
                ${UserColumns.NAME} TEXT NULL,
                ${UserColumns.COMPANY} TEXT NULL,
                ${UserColumns.FOLLOWERS} INT NULL,
                ${UserColumns.FOLLOWING} INT NULL,                
                ${UserColumns.LOCATION} TEXT NULL,
                ${UserColumns.PUBLIC_REPOS} INT NULL,
                ${UserColumns.URL} TEXT NOT NULL                                
                ) 
                """
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_USER)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

}