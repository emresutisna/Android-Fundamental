package com.cilegondev.dgithubuser.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.cilegondev.dgithubuser.R
import com.cilegondev.dgithubuser.adapters.UserListAdapter
import com.cilegondev.dgithubuser.adapters.UserListAdapter.Companion.NORMAL_LIST
import com.cilegondev.dgithubuser.models.UserDetailViewModel
import kotlinx.android.synthetic.main.fragment_follower.view.*
import kotlinx.android.synthetic.main.fragment_saved.*

/**
 * A simple [Fragment] subclass.
 */
class FollowerFragment : Fragment() {
    private val userDetailViewModel: UserDetailViewModel by activityViewModels()
    private lateinit var adapter: UserListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_follower, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = UserListAdapter()
        adapter.notifyDataSetChanged()
        view.recyclerview.layoutManager = LinearLayoutManager(context)
        view.recyclerview.adapter = adapter
        showLoading(true)
        userDetailViewModel.getFollowers().observe(viewLifecycleOwner, Observer { users ->
            if (users != null) {
                adapter.setData(users, NORMAL_LIST)
                if (users.isEmpty()) {
                    layNoData.visibility = View.VISIBLE
                } else {
                    layNoData.visibility = View.GONE
                }
                showLoading(false)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            view?.progressBar?.visibility = View.VISIBLE
        } else {
            view?.progressBar?.visibility = View.GONE
        }
    }
}
