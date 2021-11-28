package com.cilegondev.dgithubuser

import android.content.Intent
import android.content.res.TypedArray
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cilegondev.dgithubuser.adapters.UserListAdapter
import com.cilegondev.dgithubuser.models.User
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navController = findNavController(R.id.container)
        bottom_navigation.setupWithNavController(navController)

        val langMenuItem = bottom_navigation.menu.findItem(R.id.navigation_lang)
        langMenuItem.setOnMenuItemClickListener {
            val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(intent)
            true
        }
    }
}

