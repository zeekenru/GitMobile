package com.kovapss.gitmobile.view.search.delegateAdapters

import android.graphics.Color
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.google.gson.internal.bind.util.ISO8601Utils
import com.kovapss.gitmobile.R
import com.kovapss.gitmobile.entities.issue.Issue
import com.kovapss.gitmobile.view.profile.content.DelegateAdapter
import kotlinx.android.synthetic.main.issue_item.view.*
import java.text.ParsePosition


class IssuesDelegateAdapter(private val listener : (Issue) -> Unit) : DelegateAdapter<Issue>() {
    override fun onBind(item: Issue, viewHolder: ViewHolder) = with(viewHolder.itemView){
        issue_title.text = item.title
        issue_author.text = item.author.login
        issue_create_date.text = TimeAgo.using(ISO8601Utils.parse(item.createDate, ParsePosition(0)).time)
        issue_comment_count.text = item.commentCount.toString()
        if (item.state == "opened"){
            issue_state.text = "OPENED"
            issue_state.setTextColor(Color.GREEN)
        } else {
            issue_state.text = "CLOSED"
            issue_state.setTextColor(Color.RED)
        }
        setOnClickListener { listener(item) }
    }

    override fun getLayoutId(): Int = R.layout.issue_item

    override fun isForViewType(data: MutableList<*>, position: Int): Boolean = data[position] is Issue

}