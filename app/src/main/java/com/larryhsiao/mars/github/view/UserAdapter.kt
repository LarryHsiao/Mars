package com.larryhsiao.mars.github.view

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.larryhsiao.mars.github.User
import com.silverhetch.aura.view.ViewHolder
import com.larryhsiao.mars.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_user.view.*

/**
 * Adapter to show users on list.
 */
class UserAdapter() : RecyclerView.Adapter<ViewHolder>() {
    private val users = ArrayList<User>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_user,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.rootView.userName.text = users[position].userName()
        Picasso.get().load(Uri.parse(users[position].imageUrl())).placeholder(
            CircularProgressDrawable(holder.rootView.context).apply {
                setStyle(
                    CircularProgressDrawable.DEFAULT
                )
            }
        ).into(holder.rootView.userImage)
    }

    fun loadUp(users:List<User>){
        this.users.clear()
        this.users.addAll(users)
        notifyDataSetChanged()
    }

    fun append(newUsers:List<User>){
        if (newUsers.isEmpty()){
            return
        }
        this.users.addAll(newUsers)
        notifyItemRangeInserted(users.size - newUsers.size, newUsers.size)
    }
}