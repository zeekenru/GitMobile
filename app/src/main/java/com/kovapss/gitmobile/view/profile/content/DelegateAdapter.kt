package com.kovapss.gitmobile.view.profile.content

import android.view.View
import com.example.delegateadapter.delegate.BaseDelegateAdapter
import com.example.delegateadapter.delegate.BaseViewHolder
import com.kovapss.gitmobile.entities.DelegatesAdapterModel
import com.orhanobut.logger.Logger
import kotlinx.android.extensions.LayoutContainer


abstract class DelegateAdapter<T> : BaseDelegateAdapter<DelegateAdapter.ViewHolder, T>() {


    abstract fun onBind(item: T, viewHolder: ViewHolder)

    final override fun onBindViewHolder(view: View, item: T, viewHolder: ViewHolder) =
            onBind(item, viewHolder)

    override fun createViewHolder(parent: View?): ViewHolder = ViewHolder(parent)



    class ViewHolder(override val containerView: View?) : BaseViewHolder(containerView), LayoutContainer
}