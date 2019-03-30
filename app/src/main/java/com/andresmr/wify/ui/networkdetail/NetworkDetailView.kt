package com.andresmr.wify.ui.networkdetail

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.nfc.tech.NdefFormatable
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import com.andresmr.wify.DependencyInjector
import com.andresmr.wify.R
import com.andresmr.wify.entity.WifiNetwork
import com.andresmr.wify.ui.extensions.generateNdefMessage
import com.andresmr.wify.ui.extensions.writeWifiNetwork
import kotlinx.android.synthetic.main.write_tag_view.*

class NetworkDetailView : AppCompatActivity() {

    private lateinit var nfcAdapter: NfcAdapter
    private lateinit var viewModel: NetworkDetailViewModel
    private lateinit var viewModelFactory: NetworkDetailViewModelFactory
    private var tag: Tag? = null
    private lateinit var wifiNetwork: WifiNetwork

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.write_tag_view)
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        viewModel = createViewModel()
        observeViewModel()
        val ssid = intent.getStringExtra("ssid")
        viewModel.refresh(ssid)
    }

    private fun createViewModel(): NetworkDetailViewModel {
        viewModelFactory = DependencyInjector.provideNetworkDetailViewModelFactory()
        return ViewModelProvider(ViewModelStore(), viewModelFactory).get(NetworkDetailViewModel::class.java)
    }

    private fun observeViewModel() {
        viewModel.getNetwork().observe(this, Observer {
            it?.let {
                if (it.isLoading) {
                    //TODO show loading
                } else {
                    it.network?.let {
                        wifiNetwork = it
                        showSSID(it.ssid)
                        showScanInfo()
                    }
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        enableNFCInForeground()
    }

    override fun onPause() {
        super.onPause()
        pauseNFCInForeground()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let {
            if (NfcAdapter.ACTION_TECH_DISCOVERED == intent.action || NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action) {
                tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG)
                tag?.let {
                    writeNetOnTag(wifiNetwork)
                }
            }
        }
    }

    private fun enableNFCInForeground() {
        val pendingIntent = PendingIntent.getActivity(this, 0,
                Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0)
        val nfcIntentFilter = IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED)
        val filters = arrayOf(nfcIntentFilter)
        val techLists = arrayOf(arrayOf(Ndef::class.java.name),
                arrayOf(NdefFormatable::class.java.name))
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, filters, techLists)
    }

    private fun pauseNFCInForeground() {
        nfcAdapter.disableForegroundDispatch(this)
    }

    private fun showScanInfo() {
        scanningInfo.text = getString(R.string.scan_info)
    }

    private fun hideScanInfo() {
        scanningInfo.text = null
    }

    private fun showSSID(ssidInfo: String) {
        ssid.text = ssidInfo
    }

    private fun writeNetOnTag(wifiNetwork: WifiNetwork) {
        if (tag!!.writeWifiNetwork(wifiNetwork.generateNdefMessage())) {
            showWriteOnTagSuccessfulInfo()
            vibrate()
        } else {
            showWriteOnTagErrorInfo()
            vibrate()
        }
    }

    private fun showWriteOnTagSuccessfulInfo() {
        scanningInfo.text = getString(R.string.write_tag_success)
    }

    private fun showWriteOnTagErrorInfo() {
        scanningInfo.text = getString(R.string.write_tag_error)
    }

    private fun vibrate() {
        val v = getSystemService(VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            v.vibrate(500)
        }
    }
}