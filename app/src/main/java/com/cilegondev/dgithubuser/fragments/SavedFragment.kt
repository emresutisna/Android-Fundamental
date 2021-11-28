package com.cilegondev.dgithubuser.fragments

import android.database.ContentObserver
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cilegondev.dgithubuser.R
import com.cilegondev.dgithubuser.adapters.UserListAdapter
import com.cilegondev.dgithubuser.adapters.UserListAdapter.Companion.REMOVABLE
import com.cilegondev.dgithubuser.db.DatabaseContract.UserColumns.Companion.CONTENT_URI
import com.cilegondev.dgithubuser.helpers.MappingHelper
import com.cilegondev.dgithubuser.models.User
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_follower.view.*
import kotlinx.android.synthetic.main.fragment_saved.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
class SavedFragment : Fragment() {
    private lateinit var adapter: UserListAdapter
    private lateinit var uriWithId: Uri
    private lateinit var myObserver: ContentObserver

    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = UserListAdapter()
        adapter.notifyDataSetChanged()
        view.recyclerview.layoutManager = LinearLayoutManager(context)
        view.recyclerview.adapter = adapter
        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        myObserver = object : ContentObserver(handler) {
            override fun onChange(self: Boolean) {
                loadUsersAsync()
            }
        }
        context?.contentResolver?.registerContentObserver(CONTENT_URI, true, myObserver)
        if (savedInstanceState == null) {
            loadUsersAsync()
        } else {
            val list = savedInstanceState.getParcelableArrayList<User>(EXTRA_STATE)
            if (list != null) {
                adapter.setData(list, REMOVABLE)
            }
        }
        adapter.setOnItemClickCallback(object : UserListAdapter.OnItemClickCallback {
            override fun onItemClicked(user: User) {
                showSelectedUser(user)
            }
        })
        adapter.setOnItemDeleteCallback(object : UserListAdapter.OnItemDeleteCallback {
            override fun onItemDelete(user: User) {
                MaterialAlertDialogBuilder(context)
                    .setTitle(resources.getString(R.string.delete_title))
                    .setMessage(resources.getString(R.string.delete_message))
                    .setPositiveButton(resources.getString(R.string.delete_text)) { _, _ ->
                        uriWithId = Uri.parse(CONTENT_URI.toString() + "/" + user.id)
                        context?.contentResolver?.delete(uriWithId, null, null)
                        Toast.makeText(
                            context,
                            resources.getString(R.string.delete_success),
                            Toast.LENGTH_SHORT
                        ).show()
                        loadUsersAsync()
                    }
                    .setNegativeButton(resources.getString(R.string.cancel_text)) { dialog, _ ->
                        dialog.cancel()
                    }
                    .show()

            }
        })
    }

    private fun loadUsersAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            view?.progressBar?.visibility = View.VISIBLE
            val deferredNotes = async(Dispatchers.IO) {
                val cursor = context?.contentResolver?.query(CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }
            val users = deferredNotes.await()
            view?.progressBar?.visibility = View.INVISIBLE
            if (users.size > 0) {
                adapter.setData(users, REMOVABLE)
                layNoData.visibility = View.GONE
            } else {
                adapter.setData(ArrayList(), REMOVABLE)
                layNoData.visibility = View.VISIBLE
            }
        }
    }

    private fun showSelectedUser(user: User) {
        val toDetailActivity = SavedFragmentDirections.actionNavigationSavedToDetailActivity(user)
        toDetailActivity.type = "Saved"
        findNavController().navigate(toDetailActivity)
    }

    override fun onDestroy() {
        super.onDestroy()
        context?.contentResolver?.unregisterContentObserver(myObserver)
    }
}
