package com.kovapss.gitmobile.view.login

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.webkit.*
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.kovapss.gitmobile.R
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : MvpAppCompatActivity(), LoginView {

    @InjectPresenter lateinit var presenter: LoginPresenter

    companion object {
        const val GIST_AUTH_CODE = 9
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_login)
        prepareWebView()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().removeAllCookies(null)
        }
    }

    override fun loadUrl(url: String) {
        login_web_view.loadUrl(url)
    }


    override fun showProgress() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress_bar.visibility = View.INVISIBLE
    }

    override fun setOkResult() {
        Logger.d("Result is ok")
        setResult(Activity.RESULT_OK)
        finish()
    }

    private fun prepareWebView() {
        with(login_web_view) {
            webViewClient = object : WebViewClient() {

                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    Logger.d("Loading url : $url")
                    presenter.onPageStarted()
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    presenter.onPageFinished()
                }


                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                override fun shouldOverrideUrlLoading(view: WebView?,
                                                      request: WebResourceRequest): Boolean {
                    return presenter.shouldOverrideUrlLoading(request.url.toString())
                }

                override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
                    return presenter.shouldOverrideUrlLoading(url)
                }

            }
            with(settings) {
                javaScriptEnabled = true
                allowFileAccess = false
            }

        }

    }

    override fun hideKeyboard() {
        (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                .hideSoftInputFromWindow(login_activity_root.windowToken, 0)
    }

}



