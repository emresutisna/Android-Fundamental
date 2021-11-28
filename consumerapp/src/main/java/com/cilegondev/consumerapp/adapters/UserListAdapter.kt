package com.cilegondev.consumerapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.cilegondev.consumerapp.R
import com.cilegondev.consumerapp.models.User
import com.cilegondev.consumerapp.utils.GlideApp
import kotlinx.android.synthetic.main.item_row_user.view.*


class UserListAdapter : RecyclerView.Adapter<UserListAdapter.UserViewHolder>() {
    private val mData = ArrayList<User>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setData(items: ArrayList<User>) {
        mData.clear()
        mData.addAll(items)
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
                itemView.setOnClickListener { onItemClickCallback.onItemClicked(user) }
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
}