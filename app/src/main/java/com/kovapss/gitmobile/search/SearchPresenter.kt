package com.kovapss.gitmobile.search

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.kovapss.gitmobile.Scopes.APP_SCOPE
import com.kovapss.gitmobile.Scopes.NETWORK_SCOPE
import com.kovapss.gitmobile.entities.FiltersData
import com.kovapss.gitmobile.model.SearchRepository
import toothpick.Toothpick
import javax.inject.Inject

@InjectViewState
class SearchPresenter : MvpPresenter<SearchView>() {

//    @Inject lateinit var repository : SearchRepository


    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
//        Toothpick.inject(this, Toothpick.openScopes(APP_SCOPE, NETWORK_SCOPE))
    }

    fun filtersChanged(data : FiltersData){

    }
}