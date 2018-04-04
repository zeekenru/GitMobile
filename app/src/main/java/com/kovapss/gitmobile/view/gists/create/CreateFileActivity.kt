package com.kovapss.gitmobile.view.gists.create

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window
import android.widget.ArrayAdapter
import com.jakewharton.rxbinding2.widget.RxTextView
import com.kovapss.gitmobile.R
import com.orhanobut.logger.Logger
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function3
import kotlinx.android.synthetic.main.activity_create_file.*

class CreateFileActivity : AppCompatActivity(), View.OnFocusChangeListener {

    companion object {
        const val CREATE_FILE_REQUEST_CODE = 0x1
        const val FILE_DATA_INTENT_KEY = "file_data"
    }

    private val cd = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_create_file)
        val adapter = ArrayAdapter.createFromResource(this, R.array.file_extensions,
                android.R.layout.simple_spinner_item)
        file_extensions_textview.setAdapter(adapter)

        val filenameObservable = RxTextView.textChanges(gist_filename_edit_text)
                .skipInitialValue()
                .map { it.isNotEmpty() }

        val contentObservable = RxTextView.textChanges(file_content_edit_text)
                .skipInitialValue()
                .map { it.isNotEmpty() }

        val extensionObservable = RxTextView.textChangeEvents(file_extensions_textview)
                .skipInitialValue()
                .map { it.text().isNotEmpty()}
        cd.add(Observable.combineLatest(filenameObservable, contentObservable, extensionObservable,
                Function3<Boolean, Boolean, Boolean, Boolean> {t1, t2, t3 ->  t1 && t2 && t3})
                .subscribe({gist_save_file_btn.isEnabled = true}))

        gist_save_file_btn.setOnClickListener {
            val filename = "${gist_filename_edit_text.text}.${file_extensions_textview.text}"
            val gistFile = GistFile(filename, file_content_edit_text.text.toString())
            val intent = Intent().apply { putExtra(FILE_DATA_INTENT_KEY, gistFile)}
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

    }

    override fun onFocusChange(v: View, hasFocus: Boolean) {
        if (v != gist_filename_edit_text && gist_filename_edit_text.text.isEmpty()) {
            filename_textinput_layout.error = "Необходимо назвать файл"
            gist_save_file_btn.isEnabled = false
        }
        if (v != file_content_edit_text && file_content_edit_text.text.isEmpty()) {
            filename_textinput_layout.error = "Содержимое не должно быть пустым"
            gist_save_file_btn.isEnabled = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cd.clear()
    }
}
