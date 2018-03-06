package com.kovapss.gitmobile.view.gists.detail

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.google.gson.internal.bind.util.ISO8601Utils
import com.kovapss.gitmobile.R
import com.kovapss.gitmobile.entities.Comment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.gist_comment.view.*
import java.text.ParsePosition


class GistCommentsListAdapter(private val comments: List<Comment>) : RecyclerView.Adapter<GistCommentsListAdapter.ViewHolder>() {

    companion object {
        private const val NONE_ASSOTIATION = "NONE"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.gist_comment, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
            = holder.bind(comments[position])


    override fun getItemCount(): Int = comments.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(comment: Comment) {
            with(comment) {
                Picasso.with(itemView.context)
                        .load(commentOwner.avatarUrl)
                        .noFade()
                        .into(itemView.gist_comment_avatar)
                if (commentOwner.login != null) {
                    itemView.gist_comment_login.text = commentOwner.login
                } else {
                    itemView.gist_comment_login.text = "Anonymous"
                }
//                if (authorAssociation == NONE_ASSOTIATION){
//                    itemView.gist_comment_owner_status.visibility = View.INVISIBLE
//                }
                itemView.gist_comment_date.text = TimeAgo.using(ISO8601Utils.parse(createDate, ParsePosition(0)).time)
                itemView.gist_comment_body.text = commentBody
            }
        }
    }

}