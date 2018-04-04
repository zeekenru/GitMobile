package com.kovapss.gitmobile.view.search.delegateAdapters

import com.github.marlonlom.utilities.timeago.TimeAgo
import com.google.gson.internal.bind.util.ISO8601Utils
import com.kovapss.gitmobile.R
import com.kovapss.gitmobile.entities.commit.Commit
import com.kovapss.gitmobile.view.profile.content.DelegateAdapter
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.commit_item.view.*
import java.text.ParsePosition


class CommitsDelegateAdapter(private val listener: (Commit) -> Unit) : DelegateAdapter<Commit>() {
    override fun onBind(item: Commit, viewHolder: ViewHolder) = with(viewHolder.itemView){
        Logger.d("OnBind methond, item: $item")
        if (!item.message.isNullOrEmpty()){
            commit_message.text = item.message
        } else {
            commit_message.text = "No message"
        }
        commit_author.text = item.author.name
        commit_create_date.text = TimeAgo.using(ISO8601Utils.parse(item.author.date, ParsePosition(0)).time)
        commit_comment_count.text = item.commentCount.toString()
        if (item.verification.verified != null){
            if (item.verification.verified == true) {
                commit_verification_image.setImageResource(R.drawable.ic_verified)
            }
        }

        setOnClickListener { listener(item) }
    }

    override fun getLayoutId(): Int = R.layout.commit_item

    override fun isForViewType(data: MutableList<*>, position: Int): Boolean
            = data[position] is Commit
}