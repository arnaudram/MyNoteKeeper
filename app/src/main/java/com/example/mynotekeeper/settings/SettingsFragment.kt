package com.example.mynotekeeper.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.mynotekeeper.R

class SettingsFragment:PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.pref_headers,rootKey)

    }
}