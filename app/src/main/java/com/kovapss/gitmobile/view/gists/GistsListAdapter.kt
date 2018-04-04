package com.kovapss.gitmobile.view.gists

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kovapss.gitmobile.R
import com.kovapss.gitmobile.entities.Gist
import com.orhanobut.logger.Logger
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.gist_item.view.*


class GistsListAdapter(private val data: MutableList<Gist>,
                       private val listener: (Gist) -> Unit)
    : RecyclerView.Adapter<GistsListAdapter.ViewHolder>() {

    fun addItems(items : List<Gist>) {
        val startPos = data.size + 1
        data.addAll(items)
        notifyItemRangeInserted(startPos, items.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.gist_item, parent, false)
        return ViewHolder(view, listener)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position])

    override fun getItemCount(): Int = data.size

    class ViewHolder(itemView: View, private val listener: (Gist) -> Unit) : RecyclerView.ViewHolder(itemView) {
        fun bind(gist: Gist) {
            with(gist) {
                if (owner !== null) {
                    if (owner.avatarUrl != null) {
                        Picasso.with(itemView.gist_user_avatar.context)
                                .load(owner.avatarUrl)
                                .noFade()
                                .error(R.drawable.ic_account)
                                .into(itemView.gist_user_avatar)
                    }
                    itemView.description_text.text = "${owner.login}/${files.keys.first()}"
                } else {
                    Picasso.with(itemView.gist_user_avatar.context).load(R.drawable.default_avatar)
                            .noFade()
                            .into(itemView.gist_user_avatar)
                    itemView.description_text.text = "Anonymous/${files.keys.first()}"
                }

                if (!files.values.first().language.isNullOrEmpty()) {
                    itemView.language_name_text.text = files.values.first().language
                } else itemView.language_name_text.visibility = View.INVISIBLE

                itemView.comments_count_text.text = comments.toString()
                itemView.setOnClickListener { listener(this) }
            }

        }
    }
}




