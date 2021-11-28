package com.cilegondev.dgithubuser.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.cilegondev.dgithubuser.R
import com.cilegondev.dgithubuser.models.User


class UserListAdapter internal constructor(private val listUser: ArrayList<User>, val context: Context) : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row_user, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = listUser[position]

        Glide.with(holder.itemView.context)
            .load(user.avatar)
            .apply(RequestOptions())
            .into(holder.imgAvatar)

        holder.tvNama.text = user.name
        holder.tvCompany.text = user.company
        if(user.follower < 500){
            holder.card.setCardBackgroundColor(context.resources.getColor(R.color.pink))
        }else if(user.follower < 1000){
            holder.card.setCardBackgroundColor(context.resources.getColor(R.color.green))
        }else{
            holder.card.setCardBackgroundColor(context.resources.getColor(R.color.blue))
        }
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(user) }
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgAvatar: ImageView = itemView.findViewById(R.id.imgAvatar)
        var tvNama: TextView = itemView.findViewById(R.id.tvNama)
        var tvCompany: TextView = itemView.findViewById(R.id.tvCompany)
        var card: CardView = itemView.findViewById(R.id.card)
    }
    interface OnItemClickCallback{
        fun onItemClicked(user: User)
    }
}