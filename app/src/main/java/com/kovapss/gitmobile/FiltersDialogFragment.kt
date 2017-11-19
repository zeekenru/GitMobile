package com.kovapss.gitmobile

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.kovapss.gitmobile.entities.FiltersData
import kotlinx.android.synthetic.main.filters_dialog_fragment.*


class FiltersDialogFragment : Fragment() {


    private lateinit var listener: FilterChangeListener

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val spinnerAdapter: ArrayAdapter<CharSequence> = ArrayAdapter(context,
                android.R.layout.simple_spinner_item, resources.getStringArray(R.array.content_types))
        content_spinner.adapter = spinnerAdapter
        return inflater!!.inflate(R.layout.filters_dialog_fragment, container, false)
    }

    interface FilterChangeListener {
        fun onChange(data: FiltersData)
    }
}