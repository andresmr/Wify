package com.andresmr.wify.ui

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.nfc.tech.Ndef
import android.nfc.tech.NdefFormatable
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.appcompat.app.AppCompatActivity
import com.andresmr.wify.R
import com.andresmr.wify.entity.Net
import com.andresmr.wify.presenter.ScanTagPresenter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast


class MainActivity : AppCompatActivity(), ScanTagPresenter.View {
    private lateinit var nfcAdapter: NfcAdapter
    private lateinit var presenter: ScanTagPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        presenter = ScanTagPresenter(this)
        writeTag.setOnClickListener { presenter.onWriteTagClick() }
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
            if (NfcAdapter.ACTION_TECH_DISCOVERED == intent.action) {
                val rawMessage = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
                rawMessage?.let {
                    val nDefMessages = rawMessage.map {
                        it as NdefMessage
                    }.toTypedArray()

                    val nfcMessage = String(nDefMessages.first().records[1].payload)
                    val net = Gson().fromJson(nfcMessage, Net::class.java)
                    presenter.onTagRead(net)
                }
                toast("Unknown tag")
            } else {
                toast("Touch NFC tag to read data")
            }
        }
        toast("Touch NFC tag to read data")
    }

    override fun enableNFCInForeground() {
        val pendingIntent = PendingIntent.getActivity(this, 0,
                Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0)
        val nfcIntentFilter = IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED)
        val filters = arrayOf(nfcIntentFilter)
        val techLists = arrayOf(arrayOf(Ndef::class.java.name), arrayOf(NdefFormatable::class.java.name))
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, filters, techLists)
    }

    override fun pauseNFCInForeground() {
        nfcAdapter.disableForegroundDispatch(this)
    }

    override fun showSSID(ssidInfo: String) {
        ssid.text = ssidInfo
    }

    override fun showPassword(pass: String) {
        password.text = pass
    }

    override fun vibrate() {
        val v = getSystemService(VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            v.vibrate(500)
        }
    }

    override fun openWriteTagView() {
        startActivity(Intent(this, WriteTagView::class.java))
    }
}