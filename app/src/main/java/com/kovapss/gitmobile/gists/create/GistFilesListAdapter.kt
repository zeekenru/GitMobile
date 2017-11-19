package com.kovapss.gitmobile.gists.create

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kovapss.gitmobile.R
import kotlinx.android.synthetic.main.gist_file_item.view.*


class GistFilesListAdapter(private val data: List<GistFile>, private val onClickListener: (GistFile) -> Unit,
                           private val onDeleteClickListener: (GistFile) -> Unit)
    : RecyclerView.Adapter<GistFilesListAdapter.ViewHolder>() {

    override fun getItemCount(): Int = data.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(
                R.layout.gist_file_item, parent, false), onClickListener, onDeleteClickListener)
    }

    class ViewHolder(itemView: View,
                     private val onClickListener: (GistFile) -> Unit,
                     private val onDeleteClickListener: (GistFile) -> Unit)
        : RecyclerView.ViewHolder(itemView) {
        fun bind(file: GistFile) {
            itemView.gist_file_description.text = file.filename
            itemView.setOnClickListener { onClickListener(file) }
            itemView.delete_image_btn.setOnClickListener({onDeleteClickListener(file)})
        }
    }
}