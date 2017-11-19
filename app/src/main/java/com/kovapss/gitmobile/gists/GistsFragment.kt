package com.kovapss.gitmobile.gists


import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter

import com.kovapss.gitmobile.R
import com.kovapss.gitmobile.entities.Gist
import com.kovapss.gitmobile.gists.create.CreateGistActivity
import kotlinx.android.synthetic.main.fragment_gists.*


class GistsFragment : MvpAppCompatFragment(), GistsView {

    @InjectPresenter lateinit var presenter : GistsPresenter

    private lateinit var adapter : GistsListAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
            = inflater!!.inflate(R.layout.fragment_gists, container, false)


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTabLayout()
        initRecyclerView()
        gists_fab.setOnClickListener { presenter.fabOnClick() }
    }

    override fun showGists(gists: List<Gist>) {
        adapter = GistsListAdapter(gists, { presenter.itemOnClick(it) })
        gists_recycler_view.adapter = adapter
    }

    override fun showProgress() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress_bar.visibility = View.INVISIBLE
    }

    override fun showInternetError() {
    }

    override fun showUndefinedError(msg: String) {

    }

    override fun openGistCreateView() {
        startActivity(Intent(context, CreateGistActivity::class.java))
    }

    private fun initRecyclerView(){
        gists_recycler_view.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        gists_recycler_view.hasFixedSize()
    }

    private fun initTabLayout() {
        gists_tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) presenter.tabSelected(tab.tag.toString())
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }
        })
    }
}
