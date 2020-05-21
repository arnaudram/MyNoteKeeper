package com.example.mynotekeeper.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.mynotekeeper.R

class NotificationPreferenceFragment:PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.pref_notification,rootKey)
    }
}