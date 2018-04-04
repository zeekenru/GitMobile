package com.kovapss.gitmobile.view.profile.content

import com.kovapss.gitmobile.R
import com.kovapss.gitmobile.entities.repository.Repository
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.repository_item.*
import kotlinx.android.synthetic.main.repository_item.view.*


class RepositoryDelegateAdapter(private val listener: (Repository) -> Unit)
    : DelegateAdapter<Repository>() {

    override fun onBind(item: Repository, viewHolder: ViewHolder) = with(viewHolder) {
        with(item){
            Picasso.with(itemView.context)
                    .load(owner.avatarUrl)
                    .noFade()
                    .into(itemView.repo_owner_avatar)

            repository_name.text = name

            if (!description.isNullOrEmpty()) itemView.repo_description_text.text = description

            forks_count_text.text = forksCount.toString()

            stars_count_text.text = stargazersCount.toString()

            if (!language.isNullOrEmpty()) itemView.language_name_text.text = language

            itemView.setOnClickListener { listener(this) }
        }

    }



    override fun getLayoutId(): Int = R.layout.repository_item

    override fun isForViewType(data: MutableList<*>, position: Int): Boolean
            = data[position] is Repository


}