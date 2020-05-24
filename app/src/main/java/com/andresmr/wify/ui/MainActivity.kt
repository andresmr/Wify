package com.andresmr.wify.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.andresmr.wify.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        findNavController(R.id.nav_host_fragment).addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.addNetworkView) {
                supportActionBar?.hide()
            } else {
                supportActionBar?.show()
            }
        }
    }
}
