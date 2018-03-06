package com.kovapss.gitmobile.view.repositories.detail.issues.detail

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.google.gson.internal.bind.util.ISO8601Utils
import com.kovapss.gitmobile.R
import com.kovapss.gitmobile.entities.Comment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.comment_item.view.*
import java.text.ParsePosition


class IssueCommentsAdapter(private val data: List<Comment>) : RecyclerView.Adapter<IssueCommentsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comment_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position])

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(comment: Comment) {
            with(comment) {
                Picasso.with(itemView.context)
                        .load(commentOwner.avatarUrl)
                        .noFade()
                        .into(itemView.comment_avatar)
                if (commentOwner.login != null) {
                    itemView.comment_login.text = commentOwner.login
                } else {
                    itemView.comment_login.text = "Anonymous"
                }
                itemView.comment_date.text = TimeAgo.using(ISO8601Utils.parse(createDate, ParsePosition(0)).time)
                itemView.comment_body.text = commentBody
            }
        }


    }
}