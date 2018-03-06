package com.kovapss.gitmobile.view.repositories.detail.files

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.kovapss.gitmobile.R
import com.kovapss.gitmobile.entities.repository.RepositoryFile
import kotlinx.android.synthetic.main.repository_file_item.view.*


class RepositoryFilesAdapter(private val data: List<RepositoryFile>,
                             private val itemClickListener: (RepositoryFile) -> Unit,
                             private val menuMoreClickListener: (MenuItem) -> Boolean
) : RecyclerView.Adapter<RepositoryFilesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.repository_file_item, parent, false)
        return ViewHolder(view, itemClickListener, menuMoreClickListener)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    class ViewHolder(itemView: View, val itemClickListener: (RepositoryFile) -> Unit,
                     val menuMoreClickListener: (MenuItem) -> Boolean) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: RepositoryFile) = with(item) {
            if (type == "file") {
                itemView.repo_file_type_image.setImageResource(R.drawable.ic_file_primary)
            } else {
                itemView.repo_file_type_image.setImageResource(R.drawable.ic_folder_primary)
            }
            itemView.repo_file_filename.text = name
            itemView.setOnClickListener { itemClickListener(item) }
        }
    }
}