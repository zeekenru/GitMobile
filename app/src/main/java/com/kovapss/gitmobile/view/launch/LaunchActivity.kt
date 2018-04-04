package com.kovapss.gitmobile.view.launch

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.kovapss.gitmobile.Constants.OAUTH_REQUEST_CODE
import com.kovapss.gitmobile.R
import com.kovapss.gitmobile.view.login.LoginActivity
import com.kovapss.gitmobile.view.main.MainActivity
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_launch.*


class LaunchActivity : MvpAppCompatActivity(), LaunchView {


    @InjectPresenter
    lateinit var presenter: LaunchPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
        login_btn.setOnClickListener({ presenter.loginClick() })
        skip_text.setOnClickListener({ presenter.skipClick() })
    }

    override fun onResume() {
        super.onResume()
        hideProgress()
    }

    override fun showInternetError() {
        AlertDialog.Builder(this)
                .setTitle(getString(R.string.connection_error))
                .setNeutralButton(getString(R.string.try_again), { _, _ -> presenter.clickTryConnect() })
                .setCancelable(true)
                .create()
                .show()
    }

    override fun showUndefinedError() {

    }

    override fun showProgress() {
        progress_bar.visibility = View.VISIBLE
        login_btn.visibility = View.INVISIBLE
        skip_text.visibility = View.INVISIBLE

    }

    override fun hideProgress() {
        progress_bar.visibility = View.INVISIBLE
        login_btn.visibility = View.VISIBLE
        skip_text.visibility = View.VISIBLE
    }

    override fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun startLoginActivity() {
        startActivityForResult(Intent(this, LoginActivity::class.java), OAUTH_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Logger.d("OnActivityResult, requestCode : $requestCode")
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                OAUTH_REQUEST_CODE -> presenter.oauthResult()
            }
        }
    }

    override fun showWelcome(userName: String) {
        Toast.makeText(this, "Hi, $userName", Toast.LENGTH_SHORT).show()
    }
}
