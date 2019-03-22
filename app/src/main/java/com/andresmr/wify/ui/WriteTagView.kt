package com.andresmr.wify.ui

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
import com.andresmr.wify.R
import com.andresmr.wify.domain.CreateWifiNetworkInteractor
import com.andresmr.wify.entity.WifiNetwork
import com.andresmr.wify.presenter.WriteTagPresenter
import com.andresmr.wify.ui.util.NFCUtil
import kotlinx.android.synthetic.main.write_tag_view.*

class WriteTagView : AppCompatActivity(), WriteTagPresenter.View {

    private lateinit var nfcAdapter: NfcAdapter
    private lateinit var presenter: WriteTagPresenter
    private lateinit var nfcUtil: NFCUtil
    private var tag: Tag? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.write_tag_view)
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        presenter = WriteTagPresenter(this, CreateWifiNetworkInteractor())
        nfcUtil = NFCUtil()
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let {
            if (NfcAdapter.ACTION_TECH_DISCOVERED == intent.action || NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action) {
                tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG)
                tag?.let {
                    presenter.onTagDetected(ssid.editText!!.text.toString(), password.editText!!.text.toString())
                }
            }
        }
    }

    override fun enableNFCInForeground() {
        val pendingIntent = PendingIntent.getActivity(this, 0,
                Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0)
        val nfcIntentFilter = IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED)
        val filters = arrayOf(nfcIntentFilter)
        val techLists = arrayOf(arrayOf(Ndef::class.java.name),
                arrayOf(NdefFormatable::class.java.name))
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, filters, techLists)
    }

    override fun pauseNFCInForeground() {
        nfcAdapter.disableForegroundDispatch(this)
    }

    override fun showScanInfo() {
        scanningInfo.text = getString(R.string.scan_info)
    }

    override fun hideScanInfo() {
        scanningInfo.text = null
    }

    override fun showErrorOnSSID() {
        ssid.error = getString(R.string.error_field_null)
    }

    override fun showErrorOnPassword() {
        password.error = getString(R.string.error_field_null)

    }

    override fun writeNetOnTag(wifiNetwork: WifiNetwork) {
        if (nfcUtil.writeOnTag(wifiNetwork, tag!!)) {
            presenter.onWriteOnTagSuccessful()
        } else {
            presenter.onWriteOnTagError()
        }
    }

    override fun showWriteOnTagSuccessfulInfo() {
        scanningInfo.text = getString(R.string.write_tag_success)
    }

    override fun showWriteOnTagErrorInfo() {
        scanningInfo.text = getString(R.string.write_tag_error)
    }

    override fun vibrate() {
        val v = getSystemService(VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            v.vibrate(500)
        }
    }
}