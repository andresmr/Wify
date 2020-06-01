package com.andresmr.wify.ui

import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.andresmr.wify.R
import com.andresmr.wify.ui.networkdetail.NetworkDetailView
import kotlinx.android.synthetic.main.main_activity.*

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

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        intent?.let {
            if (NfcAdapter.ACTION_TECH_DISCOVERED == intent.action || NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action) {
                if (findNavController(R.id.nav_host_fragment).currentDestination?.id == R.id.networkDetailView) {
                    val tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG) as Tag
                    (nav_host_fragment.childFragmentManager.fragments[0] as NetworkDetailView).onTagDetected(
                        tag
                    )
                }
            }
        }
    }
}
