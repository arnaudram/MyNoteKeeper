package com.example.mynotekeeper.settings

import android.os.Bundle
import android.text.InputType
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceFragmentCompat
import com.example.mynotekeeper.R

class GeneralPreferenceFragment:PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
       setPreferencesFromResource(R.xml.pref_general,rootKey)

        findPreference<EditTextPreference>("edit_preference_email")
            ?.setOnBindEditTextListener {
                 it.inputType=InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            }
        findPreference<EditTextPreference>("edit_preference_name")?.setOnBindEditTextListener {
            it.inputType=InputType.TYPE_TEXT_VARIATION_PERSON_NAME
        }
    }
}