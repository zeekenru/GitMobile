package com.kovapss.gitmobile.view.profile.overview


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.kovapss.gitmobile.R
import com.kovapss.gitmobile.entities.User
import com.orhanobut.logger.Logger
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profile_overview.*


class ProfileOverviewFragment : MvpAppCompatFragment(), UserProfileOverviewView {

    @InjectPresenter lateinit var presenter: ProfileOverviewPresenter

    @ProvidePresenter
    fun providePresenter(): ProfileOverviewPresenter{
        return ProfileOverviewPresenter(arguments!!.getParcelable(USER_KEY))
    }

    companion object {

        const val USER_KEY = "user_key"

        @Volatile
        private var INSTANCE: ProfileOverviewFragment? = null

        fun getInstance(bundle: Bundle): ProfileOverviewFragment =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: createFragment(bundle).also { INSTANCE = it }
                }

        private fun createFragment(bundle: Bundle): ProfileOverviewFragment {
            val fragment = ProfileOverviewFragment()
            fragment.arguments = bundle
            Logger.d("creating profileOverviewFragment, bundle user: ${bundle.getParcelable<User>(USER_KEY)}")
            return fragment
        }

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
            = inflater.inflate(R.layout.fragment_profile_overview, container, false)


    override fun setUserData(user: User) {
        Logger.d("Setting user data $user")
        with(user) {
            Picasso.with(context)
                    .load(avatarUrl)
                    .noFade()
                    .into(user_profile_avatar)
            user_profile_name.text = name
            if (!email.isNullOrEmpty()) {
                user_profile_email.text = email
            } else {
                user_profile_email.text = "Empty"
            }
            if (!location.isNullOrEmpty()) {
                user_profile_location.text = location
            } else {
                user_profile_location.text = "Empty"
            }
            if (!bio.isNullOrEmpty()) {
                user_profile_bio.text = bio
            } else {
                user_profile_bio.text = "No bio"
            }
            user_followers_value.text = followers
            user_following_value.text = following
        }

    }

}
