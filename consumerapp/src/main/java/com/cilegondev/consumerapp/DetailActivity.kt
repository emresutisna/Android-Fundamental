package com.cilegondev.consumerapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.cilegondev.consumerapp.models.User
import com.cilegondev.consumerapp.models.UserDetailViewModel
import com.cilegondev.consumerapp.utils.GeneralUtils
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    private lateinit var userDetailViewModel: UserDetailViewModel
    private lateinit var mUser: User

    companion object {
        const val EXTRA_USER = "extra_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        userDetailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(UserDetailViewModel::class.java)
        var user = intent.getParcelableExtra<User>(EXTRA_USER)
        userDetailViewModel.setUser(user)
        userDetailViewModel.getUser().observe(this, Observer { user ->
            if (user != null) {
                this.mUser = user
                setData(user)
            }
        })

        imgBack.setOnClickListener { onBackPressed() }
    }

    private fun setData(user: User) {
        Glide.with(this).load(user.avatar_url).into(imgAvatar)
        tvNama.text = user.name?.toString() ?: getString(R.string.name_not_set)
        tvUsername.text = user.login
        tvCompany.text = user.company?.toString() ?: getString(R.string.company_not_set)
        tvLocation.text = user.location?.toString() ?: getString(R.string.location_not_set)
        tvFollower.text =
            if (user.followers > 0) GeneralUtils.formatValue(user.followers.toDouble()) else "0"
        tvFollowing.text =
            if (user.following > 0) GeneralUtils.formatValue(user.following.toDouble()) else "0"
        tvRepo.text =
            if (user.public_repos > 0) GeneralUtils.formatValue(user.public_repos.toDouble()) else "0"
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
