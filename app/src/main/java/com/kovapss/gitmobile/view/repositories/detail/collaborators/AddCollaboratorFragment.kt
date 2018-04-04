package com.kovapss.gitmobile.view.repositories.detail.collaborators

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.arellomobile.mvp.MvpAppCompatDialogFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.jakewharton.rxbinding2.widget.RxTextView
import com.kovapss.gitmobile.R
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.add_collaborator_dialog.*


class AddCollaboratorFragment : MvpAppCompatDialogFragment(), AddCollaboratorView {

    @InjectPresenter lateinit var presenter: AddCollaboratorPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = layoutInflater.inflate(R.layout.add_collaborator_dialog, container, false)

    private val cd = CompositeDisposable()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collaborators_user_search_text_view.threshold = 2
        cd.add(RxTextView.textChanges(collaborators_user_search_text_view)
                .skipInitialValue()
                .doOnNext {
                    presenter.textChanged(it.toString())
                }
                .subscribe())
    }

    override fun showUsers(usernames: List<String>) {
        val arrayAdapter = ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, usernames)
        collaborators_user_search_text_view.setAdapter(arrayAdapter)
    }

    override fun onDestroy() {
        super.onDestroy()
        cd.clear()
    }


}