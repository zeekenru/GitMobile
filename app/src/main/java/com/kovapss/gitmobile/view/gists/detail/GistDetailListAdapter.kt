package com.kovapss.gitmobile.view.gists.detail

import android.support.v4.view.GravityCompat
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.kovapss.gitmobile.R
import com.kovapss.gitmobile.entities.GistFile
import com.pddstudio.highlightjs.models.Theme
import kotlinx.android.synthetic.main.gist_detail_item.view.*
import java.net.URL


class GistDetailListAdapter(private val data: List<GistFile>,
                            private val listener: (MenuItem) -> Boolean,
                            private val showLineNumbers: Boolean)
    : RecyclerView.Adapter<GistDetailListAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position], showLineNumbers)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.gist_detail_item, parent, false)
        return ViewHolder(view, listener)
    }

    override fun getItemCount(): Int = data.size

    class ViewHolder(itemView: View, private val listener: (MenuItem) -> Boolean) : RecyclerView.ViewHolder(itemView) {
        fun bind(file: GistFile, showLineNumbers: Boolean) {
            itemView.gist_detail_codeview.setSource(URL(file.rawUrl))
            itemView.gist_detail_codeview.setZoomSupportEnabled(true)
            itemView.gist_detail_codeview.setShowLineNumbers(showLineNumbers)
            itemView.gist_detail_codeview.theme = Theme.GITHUB_GIST
            val popupMenu = PopupMenu(itemView.context, itemView)
            popupMenu.inflate(R.menu.gist_file_menu)
            popupMenu.gravity = GravityCompat.END
            itemView.gist_detail_more_btn.setOnClickListener { popupMenu.show() }
            popupMenu.setOnMenuItemClickListener(listener)
        }

    }

}