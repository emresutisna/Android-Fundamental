package com.cilegondev.dgithubuser

import android.content.Intent
import android.content.res.TypedArray
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.cilegondev.dgithubuser.adapters.UserListAdapter
import com.cilegondev.dgithubuser.models.User
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity() {
    private val list = ArrayList<User>()
    private lateinit var dataName: Array<String>
    private lateinit var dataUsername: Array<String>
    private lateinit var dataCompany: Array<String>
    private lateinit var dataLocation: Array<String>
    private lateinit var dataRepository: TypedArray
    private lateinit var dataFollower: TypedArray
    private lateinit var dataFollowing: TypedArray
    private lateinit var dataAvatar: TypedArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvListUser.setHasFixedSize(true)
        prepare()
        addItem()
        showUserList()
    }

    private fun prepare() {
        dataName = resources.getStringArray(R.array.data_name)
        dataUsername = resources.getStringArray(R.array.data_username)
        dataCompany = resources.getStringArray(R.array.data_company)
        dataLocation = resources.getStringArray(R.array.data_location)
        dataRepository = resources.obtainTypedArray(R.array.data_repository)
        dataFollower = resources.obtainTypedArray(R.array.data_followers)
        dataFollowing = resources.obtainTypedArray(R.array.data_following)
        dataAvatar = resources.obtainTypedArray(R.array.data_avatar)
    }

    private fun addItem() {
        for (position in dataName.indices) {
            val user = User(dataUsername[position],
                dataName[position],
                dataAvatar.getResourceId(position, -1),
                dataCompany[position],
                dataLocation[position],
                dataRepository.getInt(position, 0),
                dataFollower.getInt(position, 0),
                dataFollowing.getInt(position, 0)

            )
            list.add(user)
        }
    }

    private fun showUserList() {
        rvListUser.layoutManager = LinearLayoutManager(this)
        val listAdapter = UserListAdapter(list, this)
        rvListUser.adapter = listAdapter

        listAdapter.setOnItemClickCallback(object : UserListAdapter.OnItemClickCallback {
            override fun onItemClicked(user: User) {
                showSelectedUser(user)
            }
        })
    }

    private fun showSelectedUser(user: User) {
        val detailIntent = Intent(this@MainActivity, DetailActivity::class.java)
        detailIntent.putExtra(DetailActivity.EXTRA_USER, user)
        startActivity(detailIntent)
    }
}

