package com.kovapss.gitmobile.view.repositories.detail.collaborators

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kovapss.gitmobile.R
import com.kovapss.gitmobile.entities.repository.Collaborator
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.collaborator_item.view.*


class CollaboratorsAdapter(val data: MutableList<Collaborator>, private val itemCLickListener: (String) -> Unit,
                           private val deleteCLickListener: (String, Int) -> Unit)
    : RecyclerView.Adapter<CollaboratorsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.collaborator_item, parent, false)
        return ViewHolder(view, itemCLickListener, deleteCLickListener)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position])

    class ViewHolder(val view: View, private val itemCLickListener: (String) -> Unit,
                     private val deleteCLickListener: (String, Int) -> Unit)
        : RecyclerView.ViewHolder(view) {
        fun bind(collaborator: Collaborator) = with(collaborator) {
            view.collaborator_username.text = login
            Picasso.with(view.context)
                    .load(avatarUrl)
                    .noFade()
                    .into(view.collaborator_avatar)
            view.setOnClickListener { itemCLickListener(login) }
            view.collaborator_delete.setOnClickListener { deleteCLickListener(login, adapterPosition) }

        }
    }

    fun addItems(items : List<Collaborator>) {
        val startPos = data.size + 1
        data.addAll(items)
        notifyItemRangeInserted(startPos, items.size)
    }

    fun deleteItem(position : Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
    }
}