package com.kovapss.gitmobile.gists

import android.os.Build
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kovapss.gitmobile.R
import com.kovapss.gitmobile.entities.Gist
import com.orhanobut.logger.Logger
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.gist_item.view.*


class GistsListAdapter(private val data: List<Gist>,
                       private val listener: (Gist) -> Unit)
    : RecyclerView.Adapter<GistsListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.gist_item, parent, false)
        return ViewHolder(view, listener)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position])


    override fun getItemCount(): Int = data.size

    class ViewHolder(itemView: View, private val listener: (Gist) -> Unit) : RecyclerView.ViewHolder(itemView) {
        fun bind(gist: Gist) {
            Logger.d(gist.toString())
            with(gist) {
                if (public) {
                    if (owner != null) {
                        if (owner.avatarUrl != null) {
                            Logger.e("avatar url isn't NULL : ${owner.avatarUrl}")
                            Picasso.with(itemView.gist_user_avatar.context)
                                    .load(owner.avatarUrl)
                                    .noFade()
                                    .error(R.drawable.ic_account)
                                    .into(itemView.gist_user_avatar)
                        } else {
                            Logger.e("avatar url is NULL")
                        }
                        if (!owner.login.isNullOrEmpty()) {
                            itemView.description_text.text = "${owner.login}/$filename"
                        } else Logger.d(("login is null"))
                        if (!language.isNullOrEmpty()) {
                            itemView.language_name_text.text = language
                        } else itemView.language_name_text.visibility = View.INVISIBLE
                    }
                } else Logger.d("Not public")

                itemView.comments_count_text.text = comments.toString()
                itemView.setOnClickListener { listener(this) }
            }

        }
    }
}



