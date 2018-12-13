package com.andresmr.wify.ui

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.tech.Ndef
import android.nfc.tech.NdefFormatable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.andresmr.wify.NFCUtil
import com.andresmr.wify.R
import com.andresmr.wify.presenter.WriteTagPresenter
import org.jetbrains.anko.toast

class WriteTagView : AppCompatActivity(), WriteTagPresenter.View {

    private lateinit var nfcAdapter: NfcAdapter
    private lateinit var presenter: WriteTagPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.write_tag_view)
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        presenter = WriteTagPresenter(this)
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
        val messageWrittenSuccessfully = NFCUtil.createNFCMessage("ex", intent)
        toast(messageWrittenSuccessfully.ifElse("Successful Written to Tag", "Something When wrong Try Again"))
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
}

fun <T> Boolean.ifElse(primaryResult: T, secondaryResult: T) = if (this) primaryResult else secondaryResult
