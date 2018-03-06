package com.kovapss.gitmobile.view.profile.content

import com.kovapss.gitmobile.R
import com.kovapss.gitmobile.entities.Gist
import com.orhanobut.logger.Logger
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.gist_item.view.*


class GistsDelegateAdapter(private val listener: (Gist) -> Unit) : DelegateAdapter<Gist>() {

    override fun onBind(item: Gist, viewHolder: ViewHolder) = with(viewHolder) {
        with(item) {
            if (owner !== null) {
                if (owner.avatarUrl != null) {
                    Logger.d("avatar url isn't NULL : ${owner.avatarUrl}")
                    com.squareup.picasso.Picasso.with(itemView.gist_user_avatar.context)
                            .load(owner.avatarUrl)
                            .noFade()
                            .error(com.kovapss.gitmobile.R.drawable.ic_account)
                            .into(itemView.gist_user_avatar)
                }
                itemView.description_text.text = "${owner.login}/${files.keys.first()}"
            } else {
                Logger.d("OWNER IS NULL")
                Picasso.with(itemView.gist_user_avatar.context)
                        .load(com.kovapss.gitmobile.R.drawable.default_avatar)
                        .noFade()
                        .into(itemView.gist_user_avatar)
                itemView.description_text.text = "Anonymous/${files.keys.first()}"
            }

            if (!files.values.first().language.isNullOrEmpty()) {
                itemView.language_name_text.text = files.values.first().language
            } else itemView.language_name_text.visibility = android.view.View.INVISIBLE


            itemView.comments_count_text.text = comments.toString()
            itemView.setOnClickListener { listener(this) }
        }
    }

    override fun getLayoutId(): Int = R.layout.gist_item


    override fun isForViewType(items: MutableList<*>, position: Int): Boolean = items[position] is Gist

}