package com.cilegondev.dgithubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.cilegondev.dgithubuser.models.User
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.detail_content.*

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_USER = "extra_user"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val user = intent.getParcelableExtra<User>(EXTRA_USER)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        collapsing_toolbar.title = user.name

        Glide.with(this)
            .load(user.avatar)
            .apply(RequestOptions())
            .into(imgAvatar)
        tvNama.text = user.name
        tvUsername.text = user.username
        tvCompany.text = user.company
        tvLocation.text = user.location
        tvRepo.text = user.repository.toString()
        tvFollower.text = user.follower.toString()
        tvFollowing.text = user.following.toString()

        if(user.follower < 500){
            toolbar.setBackgroundColor(resources.getColor(R.color.pink))
            collapsing_toolbar.setBackground(resources.getDrawable(R.drawable.rounded_pink))
        }else if(user.follower < 1000){
            toolbar.setBackgroundColor(resources.getColor(R.color.green))
            collapsing_toolbar.setBackground(resources.getDrawable(R.drawable.rounded_green))
        }else{
            toolbar.setBackgroundColor(resources.getColor(R.color.blue))
            collapsing_toolbar.setBackground(resources.getDrawable(R.drawable.rounded_blue))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
