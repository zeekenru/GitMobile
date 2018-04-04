package com.kovapss.gitmobile.view.notifications

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kovapss.gitmobile.R
import com.kovapss.gitmobile.entities.Notification
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.notification_item.view.*


class NotificationsAdapter(private val notifications : MutableList<Notification>,
                           private val itemClickListener : (Notification) -> Unit )
    : RecyclerView.Adapter<NotificationsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notification_item, parent, false)
        return ViewHolder(view, itemClickListener)
    }

    override fun getItemCount(): Int = notifications.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(notifications[position])


    class ViewHolder(val view : View, val itemClickListener : (Notification) -> Unit ) : RecyclerView.ViewHolder(view){
        fun bind(item : Notification) = with(view){
            view.notification_item_action_name.text = item.reason
            view.notification_item_action_object.text = item.repository.fullName
            Picasso.with(view.context)
                    .load(item.repository.owner.avatarUrl)
                    .noFade()
                    .into(view.notification_item_user_avatar)
        }

    }

}