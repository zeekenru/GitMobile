package com.kovapss.gitmobile.view.repositories.detail.readme


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.tiagohm.markdownview.css.styles.Github
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.kovapss.gitmobile.R
import com.kovapss.gitmobile.entities.repository.Readme
import kotlinx.android.synthetic.main.fragment_repository_readme.*


class RepositoryReadmeFragment : MvpAppCompatFragment(), RepositoryReadmeView {


    @InjectPresenter lateinit var presenter: RepositoryReadmePresenter

    @ProvidePresenter
    fun providePresenter() = with(checkNotNull(arguments)) {
        RepositoryReadmePresenter(getParcelable(README_KEY))
    }


    companion object {
        private const val README_KEY = "readme_key"

        fun getInstance(readme: Readme): RepositoryReadmeFragment {
            val fragment = RepositoryReadmeFragment()
            val bundle = Bundle().apply {
                putParcelable(README_KEY, readme)
            }
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
       = inflater.inflate(R.layout.fragment_repository_readme, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        readme_markdown_view.addStyleSheet(Github())
    }


    override fun showReadme(readme : Readme) {
        readme_markdown_view.loadMarkdownFromUrl(readme.htmlUrl)
        readme_filename_text.text = readme.fileName
    }


}


