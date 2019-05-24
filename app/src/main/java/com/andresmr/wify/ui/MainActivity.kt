package com.andresmr.wify.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.andresmr.wify.R
import com.andresmr.wify.ui.networkslist.NetworksListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment, NetworksListFragment.newInstance())
                    .commitNow()
        }
    }
}
