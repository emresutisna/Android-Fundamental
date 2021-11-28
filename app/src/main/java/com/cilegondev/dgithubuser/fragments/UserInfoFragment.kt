package com.cilegondev.dgithubuser.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.cilegondev.dgithubuser.R
import com.cilegondev.dgithubuser.models.User
import com.cilegondev.dgithubuser.models.UserDetailViewModel
import com.cilegondev.dgithubuser.utils.GeneralUtils
import kotlinx.android.synthetic.main.fragment_user_info.*

/**
 * A simple [Fragment] subclass.
 */
class UserInfoFragment : Fragment() {
    private val userDetailViewModel: UserDetailViewModel by activityViewModels()

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
                setData(user)
                showLoading(false)
            }
        })
    }

    private fun setData(user: User){
        Glide.with(this).load(user.avatar_url).into(imgAvatar)
        tvNama.text = user.name?.toString() ?: getString(R.string.name_not_set)
        tvUsername.text = user.login
        tvCompany.text = user.company?.toString() ?: getString(R.string.company_not_set)
        tvLocation.text = user.location?.toString() ?: getString(R.string.location_not_set)
        tvFollower.text = if(user.followers > 0) GeneralUtils.formatValue(user.followers.toDouble()) else "0"
        tvFollowing.text = if(user.following > 0) GeneralUtils.formatValue(user.following.toDouble()) else "0"
        tvRepo.text = if(user.public_repos > 0) GeneralUtils.formatValue(user.public_repos.toDouble()) else "0"
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }
}
