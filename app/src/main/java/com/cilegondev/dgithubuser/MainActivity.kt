package com.cilegondev.dgithubuser

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.cilegondev.dgithubuser.fragments.SettingFragment
import com.cilegondev.dgithubuser.fragments.SettingFragment.Companion.REMINDER
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var preferences: SharedPreferences
    private lateinit var reminderReceiver: ReminderReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        preferences = applicationContext.getSharedPreferences(
            SettingFragment.PREFS_NAME,
            Context.MODE_PRIVATE
        )
        reminderReceiver = ReminderReceiver()
        val navController = findNavController(R.id.container)
        bottom_navigation.setupWithNavController(navController)
        if (!reminderReceiver.isAlarmSet(applicationContext) && preferences.getBoolean(
                REMINDER,
                true
            )
        ) reminderReceiver.setReminder(applicationContext, "09:00")
    }
}

