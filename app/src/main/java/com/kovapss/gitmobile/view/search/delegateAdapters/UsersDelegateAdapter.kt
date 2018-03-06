package com.kovapss.gitmobile.view.search.delegateAdapters

import com.kovapss.gitmobile.R
import com.kovapss.gitmobile.entities.User
import com.kovapss.gitmobile.view.profile.content.DelegateAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.user_item.view.*


class UsersDelegateAdapter(private val listener : (User) -> Unit) : DelegateAdapter<User>() {
    override fun onBind(item: User, viewHolder: ViewHolder) = with(viewHolder.itemView){
        Picasso.with(user_item_avatar.context)
                .load(item.avatarUrl)
                .noFade()
                .into(user_item_avatar)
        user_item_login.text = item.login
        setOnClickListener { listener(item) }
    }

    override fun getLayoutId(): Int = R.layout.user_item

    override fun isForViewType(data: MutableList<*>, position: Int): Boolean = data[position] is User
}