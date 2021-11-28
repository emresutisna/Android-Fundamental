package com.cilegondev.dgithubuser.fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.cilegondev.dgithubuser.R
import com.cilegondev.dgithubuser.db.DatabaseContract
import com.cilegondev.dgithubuser.db.DatabaseContract.UserColumns.Companion.CONTENT_URI
import com.cilegondev.dgithubuser.models.User
import com.cilegondev.dgithubuser.models.UserDetailViewModel
import com.cilegondev.dgithubuser.utils.GeneralUtils
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_user_info.*

/**
 * A simple [Fragment] subclass.
 */
class UserInfoFragment : Fragment(), View.OnClickListener {
    private val userDetailViewModel: UserDetailViewModel by activityViewModels()
    private lateinit var mUser: User
    private lateinit var uriWithId: Uri
    private var isFromSaved = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoading(true)
        userDetailViewModel.getUser().observe(viewLifecycleOwner, Observer { user ->
            if (user != null) {
                this.mUser = user
                setData(user)
                refreshBadge()
                showLoading(false)
            }
        })

        userDetailViewModel.getType().observe(viewLifecycleOwner, Observer { type ->
            when (type) {
                "Normal" -> isFromSaved = false
                "Saved" -> isFromSaved = true
            }
        })
        btnSave.setOnClickListener(this)
        isFromSaved = userDetailViewModel.getType().value == "Saved"
        if (isFromSaved) {
            mUser = userDetailViewModel.getUser().value as User
            uriWithId =
                Uri.parse(DatabaseContract.UserColumns.CONTENT_URI.toString() + "/" + mUser.id)
            setData(mUser)
            refreshBadge()
        }
    }

    private fun setData(user: User) {
        Glide.with(this).load(user.avatar_url).into(imgAvatar)
        tvNama.text = user.name ?: getString(R.string.name_not_set)
        tvUsername.text = user.login
        tvCompany.text = user.company ?: getString(R.string.company_not_set)
        tvLocation.text = user.location ?: getString(R.string.location_not_set)
        tvFollower.text =
            if (user.followers > 0) GeneralUtils.formatValue(user.followers.toDouble()) else "0"
        tvFollowing.text =
            if (user.following > 0) GeneralUtils.formatValue(user.following.toDouble()) else "0"
        tvRepo.text =
            if (user.public_repos > 0) GeneralUtils.formatValue(user.public_repos.toDouble()) else "0"
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    override fun onClick(v: View?) {
        MaterialAlertDialogBuilder(context)
            .setTitle(resources.getString(R.string.save_title))
            .setMessage(resources.getString(R.string.save_message))
            .setPositiveButton(resources.getString(R.string.save_text)) { _, _ ->
                if (!isFromSaved) {
                    context?.contentResolver?.insert(CONTENT_URI, mUser.convertToContentValues())
                    Toast.makeText(
                        context,
                        resources.getString(R.string.saved_success),
                        Toast.LENGTH_SHORT
                    ).show()
                    isFromSaved = true
                    refreshBadge()
                } else
                    Toast.makeText(
                        context,
                        resources.getString(R.string.user_already_exist),
                        Toast.LENGTH_LONG
                    ).show()
            }
            .setNegativeButton(resources.getString(R.string.cancel_text)) { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }

    private fun refreshBadge() {
        if (isFromSaved) {
            imgSaved.visibility = View.VISIBLE
            btnSave.visibility = View.GONE
        } else {
            imgSaved.visibility = View.GONE
            btnSave.visibility = View.VISIBLE
        }
    }
}
