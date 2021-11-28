package com.cilegondev.dgithubuser.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.cilegondev.dgithubuser.R
import com.cilegondev.dgithubuser.models.User
import com.cilegondev.dgithubuser.utils.GlideApp
import kotlinx.android.synthetic.main.item_row_user.view.*


class UserListAdapter : RecyclerView.Adapter<UserListAdapter.UserViewHolder>() {
    private val mData = ArrayList<User>()
    private lateinit var onItemClickCallback: OnItemClickCallback
    private lateinit var onItemDeleteCallback: OnItemDeleteCallback
    private var TYPE = NORMAL_LIST

    companion object {
        const val REMOVABLE = "REMOVABLE"
        const val NORMAL_LIST = "NORMAL_LIST"
        const val NO_INTERACTION = "NO_INTERACTION"
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setOnItemDeleteCallback(onItemDeleteCallback: OnItemDeleteCallback) {
        this.onItemDeleteCallback = onItemDeleteCallback
    }

    fun setData(items: ArrayList<User>, type: String) {
        mData.clear()
        mData.addAll(items)
        TYPE = type
        notifyDataSetChanged()
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: User, position: Int) {
            with(itemView) {
                GlideApp.with(context).load(user.avatar_url)
                    .placeholder(ContextCompat.getDrawable(context, R.drawable.github_logo))
                    .error(ContextCompat.getDrawable(context, R.drawable.github_logo))
                    .into(itemView.imgAvatar)
                itemView.tvUserLogin.text = user.login
                if (position % 2 != 0) {
                    itemView.card.setCardBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color.blue
                        )
                    )
                } else {
                    itemView.card.setCardBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color.green
                        )
                    )
                }
                if (TYPE == REMOVABLE) {
                    itemView.imgDelete.visibility = View.VISIBLE
                    itemView.imgDelete.setOnClickListener { onItemDeleteCallback.onItemDelete(user) }
                    itemView.setOnClickListener { onItemClickCallback.onItemClicked(user) }
                } else if (TYPE == NORMAL_LIST) {
                    itemView.setOnClickListener { onItemClickCallback.onItemClicked(user) }
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserViewHolder {
        val mView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_row_user, parent, false)
        return UserViewHolder(mView)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(mData[position], position + 1)
    }

    interface OnItemClickCallback {
        fun onItemClicked(user: User)
    }

    interface OnItemDeleteCallback {
        fun onItemDelete(user: User)
    }
}