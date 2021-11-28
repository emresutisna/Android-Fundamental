package com.cilegondev.dgithubuser

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.cilegondev.dgithubuser.db.DatabaseContract
import com.cilegondev.dgithubuser.helpers.MappingHelper
import com.cilegondev.dgithubuser.models.UserDetailViewModel
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    private lateinit var userDetailViewModel: UserDetailViewModel
    private lateinit var uriWithId: Uri

    companion object {
        const val EXTRA_USER = "extra_user"
        const val EXTRA_TYPE = "extra_type"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        var user = DetailActivityArgs.fromBundle(intent.extras as Bundle).user
        var type: String
        uriWithId = Uri.parse(DatabaseContract.UserColumns.CONTENT_URI.toString() + "/" + user.id)
        val cursor = contentResolver.query(uriWithId, null, null, null, null)
        if (cursor?.count!! > 0) {
            type = "Saved"
            user = MappingHelper.mapCursorToObject(cursor)
        } else type = "Normal"

        userDetailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(UserDetailViewModel::class.java)
        userDetailViewModel.setUser(user, type)
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
