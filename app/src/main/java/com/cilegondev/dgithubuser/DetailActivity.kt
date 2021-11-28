package com.cilegondev.dgithubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.cilegondev.dgithubuser.models.User
import com.cilegondev.dgithubuser.models.UserDetailViewModel
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    private lateinit var userDetailViewModel: UserDetailViewModel
    companion object {
        const val EXTRA_USER = "extra_user"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val user = DetailActivityArgs.fromBundle(intent.extras as Bundle).user

        userDetailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(UserDetailViewModel::class.java)

        userDetailViewModel.setUser(user.login?.toString() ?: "")

        val navController = findNavController(R.id.container)
        bottom_navigation.setupWithNavController(navController)
        imgBack.setOnClickListener(View.OnClickListener {
            onBackPressed()
        })
    }

    override fun onBackPressed() {
        finish()
    }
}
