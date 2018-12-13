package com.andresmr.wify.ui

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.nfc.tech.NdefFormatable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.andresmr.wify.ui.util.NFCUtil
import com.andresmr.wify.entity.Net
import com.andresmr.wify.R
import com.andresmr.wify.domain.CreateNetInteractor
import com.andresmr.wify.presenter.WriteTagPresenter
import kotlinx.android.synthetic.main.write_tag_view.password
import kotlinx.android.synthetic.main.write_tag_view.scanningInfo
import kotlinx.android.synthetic.main.write_tag_view.ssid
import kotlinx.android.synthetic.main.write_tag_view.writeTagButton

class WriteTagView : AppCompatActivity(), WriteTagPresenter.View {

  private lateinit var nfcAdapter: NfcAdapter
  private lateinit var presenter: WriteTagPresenter
  private lateinit var nfcUtil: NFCUtil

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.write_tag_view)
    nfcAdapter = NfcAdapter.getDefaultAdapter(this)
    presenter = WriteTagPresenter(this, CreateNetInteractor())
    nfcUtil = NFCUtil()
  }

  override fun onPause() {
    super.onPause()
    presenter.onPause()
  }

  override fun onNewIntent(intent: Intent?) {
    super.onNewIntent(intent)
    presenter.onTagDetected(ssid.editText!!.text.toString(), password.editText!!.text.toString())
  }

  override fun setListeners() {
    writeTagButton.setOnClickListener {
      presenter.onWriteTagClick(ssid.editText!!.text.toString(),
          password.editText!!.text.toString())
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

  override fun writeNetOnTag(net: Net) {
    val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)

    if (nfcUtil.writeOnTag(net, tag)) {
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
}
