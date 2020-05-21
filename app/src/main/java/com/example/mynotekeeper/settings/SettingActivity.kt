package com.example.mynotekeeper.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.preference.Preference
import androidx.preference.PreferenceManager
import com.example.mynotekeeper.R
import com.example.mynotekeeper.databinding.ActivitySettingBinding
import com.google.android.material.snackbar.Snackbar

class SettingActivity:AppCompatActivity(),SharedPreferences.OnSharedPreferenceChangeListener {
lateinit var preference:SharedPreferences
    lateinit var binding: ActivitySettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=DataBindingUtil.setContentView(this,R.layout.activity_setting)
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, SettingsFragment()).commit()
      preference=  PreferenceManager.getDefaultSharedPreferences(this)

    }

    override fun onResume() {
        super.onResume()
       preference.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preference.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key=="edit_preference_name"){
            Snackbar.make(binding.root,"your name is ${sharedPreferences?.getString(key!!,"name")}",Snackbar.LENGTH_LONG).show()
        }
    }

}
