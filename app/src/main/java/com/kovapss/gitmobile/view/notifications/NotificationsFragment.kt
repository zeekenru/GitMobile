package com.kovapss.gitmobile.view.notifications


import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter

import com.kovapss.gitmobile.R
import com.kovapss.gitmobile.entities.Notification
import kotlinx.android.synthetic.main.fragment_notifications.*


class NotificationsFragment : MvpAppCompatFragment(), NotificationsView {

    @InjectPresenter lateinit var presenter: NotificationsPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
        = inflater.inflate(R.layout.fragment_notifications, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(notification_recycler_view){
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            hasFixedSize()
        }
    }

    override fun showNotifications(notifications: List<Notification>) {
        notification_recycler_view.adapter = NotificationsAdapter(notifications.toMutableList(), {})
    }

    override fun showProgress() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress_bar.visibility = View.INVISIBLE
    }

    override fun showNetworkError() {
        network_error_placeholder.visibility = View.VISIBLE
    }

    override fun hideNetworkError() {
        network_error_placeholder.visibility = View.INVISIBLE
    }

    override fun showEmptyResultError() {
        empty_result_placeholder.visibility = View.VISIBLE
    }

    override fun hideEmptyResultError() {
        empty_result_placeholder.visibility = View.INVISIBLE
    }
}
