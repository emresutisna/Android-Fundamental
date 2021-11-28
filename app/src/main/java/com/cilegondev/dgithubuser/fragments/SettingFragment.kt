package com.cilegondev.dgithubuser.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import com.cilegondev.dgithubuser.R
import com.cilegondev.dgithubuser.ReminderReceiver
import kotlinx.android.synthetic.main.fragment_setting.*

/**
 * A simple [Fragment] subclass.
 */
class SettingFragment : Fragment(), CompoundButton.OnCheckedChangeListener, View.OnClickListener {
    private lateinit var preferences: SharedPreferences
    private lateinit var reminderReceiver: ReminderReceiver

    companion object {
        const val PREFS_NAME = "setting_pref"
        const val REMINDER = "reminder"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferences = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        reminderReceiver = ReminderReceiver()
        switchReminder.isChecked = preferences.getBoolean(REMINDER, true)
        switchReminder.setOnCheckedChangeListener(this)
        tvLanguage.setOnClickListener(this)
        tvCurrentLanguage.setOnClickListener(this)
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        if (isChecked) {
            preferences.edit {
                putBoolean(REMINDER, true)
            }
            reminderReceiver.setReminder(requireContext(), "09:00")
        } else {
            preferences.edit {
                putBoolean(REMINDER, false)
            }
            if (reminderReceiver.isAlarmSet(requireContext())) reminderReceiver.cancelReminder(
                requireContext()
            )
        }
    }

    override fun onClick(v: View?) {
        val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
        startActivity(intent)
    }


}
