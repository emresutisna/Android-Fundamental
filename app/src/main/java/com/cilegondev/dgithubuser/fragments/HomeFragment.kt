package com.cilegondev.dgithubuser.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cilegondev.dgithubuser.R
import com.cilegondev.dgithubuser.adapters.UserListAdapter
import com.cilegondev.dgithubuser.models.User
import com.cilegondev.dgithubuser.models.UserViewModel
import com.cilegondev.dgithubuser.widgets.SearchView
import kotlinx.android.synthetic.main.fragment_home.view.*

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    private lateinit var adapter: UserListAdapter
    private lateinit var userViewModel: UserViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = UserListAdapter()
        adapter.notifyDataSetChanged()
        view.rvListUser.layoutManager = LinearLayoutManager(context)
        view.rvListUser.adapter = adapter
        userViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(UserViewModel::class.java)
        showLoading(true)
        userViewModel.setUser(view.edtSearch.text?.trim().toString())

        userViewModel.getUsers().observe(viewLifecycleOwner, Observer { users ->
            if (users != null) {
                adapter.setData(users)
                showLoading(false)
            }
        })

        view.edtSearch.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                showLoading(true)
                userViewModel.setUser(view.edtSearch.text?.trim().toString())
                return@OnEditorActionListener true
            }
            false
        })

        adapter.setOnItemClickCallback(object : UserListAdapter.OnItemClickCallback{
            override fun onItemClicked(user: User) {
                showSelectedUser(user)
            }
        })

        view.edtSearch.setOnClear(object : SearchView.OnClear {
            override fun onClear(){
                userViewModel.setUser(view.edtSearch.text?.trim().toString())
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

    private fun showSelectedUser(user: User) {
        val toDetailActivity = HomeFragmentDirections.actionNavigationHomeToDetailActivity(user)
        findNavController().navigate(toDetailActivity)
    }
}
