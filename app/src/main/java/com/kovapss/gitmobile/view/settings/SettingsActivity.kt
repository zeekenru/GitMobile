package com.kovapss.gitmobile.view.settings

import android.os.Bundle
import android.preference.PreferenceActivity
import android.support.v7.preference.PreferenceFragmentCompat
import com.kovapss.gitmobile.R

class SettingsActivity : PreferenceActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
    }


    class SettingsFragment : PreferenceFragmentCompat(){
        override fun onCreatePreferences(p0: Bundle?, p1: String?) {
            addPreferencesFromResource(R.xml.preference)
        }
    }

}
