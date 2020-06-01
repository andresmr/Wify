package com.andresmr.wify.ui.networkdetail

import android.app.PendingIntent
import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.andresmr.wify.R
import com.andresmr.wify.di.Injector
import kotlinx.android.synthetic.main.network_detail_view.*

class NetworkDetailView : Fragment() {

    private lateinit var nfcAdapter: NfcAdapter
    private val safeArgs: NetworkDetailViewArgs by navArgs()
    private val viewModel: NetworkDetailViewModel by viewModels {
        Injector.provideNetworkDetailViewModelFactory(requireActivity(), safeArgs.ssid)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.network_detail_view, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.network.observe(viewLifecycleOwner, Observer { wifi ->
            showSSID(wifi.ssid)
            showScanInfo()
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        nfcAdapter = NfcAdapter.getDefaultAdapter(activity)

    }

    override fun onResume() {
        super.onResume()
        enableNFCInForeground()
    }

    override fun onPause() {
        super.onPause()
        pauseNFCInForeground()
    }

    private fun enableNFCInForeground() {
        val pendingIntent = PendingIntent.getActivity(
            activity,
            0,
            Intent(activity, requireActivity().javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
            0
        )
        val nfcIntentFilter = IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED)
        val filters = arrayOf(nfcIntentFilter)
        val techLists = arrayOf(
            arrayOf(Ndef::class.java.name),
            arrayOf(NdefFormatable::class.java.name)
        )
        nfcAdapter.enableForegroundDispatch(activity, pendingIntent, filters, techLists)
    }

    private fun pauseNFCInForeground() {
        nfcAdapter.disableForegroundDispatch(activity)
    }

    private fun showSSID(ssidInfo: String) {
        ssid.text = ssidInfo
    }

    private fun showScanInfo() {
        scanningInfo.text = getString(R.string.scan_info)
    }

    fun onTagDetected(tag: Tag) {
        if (viewModel.writeNetworkOnTag(tag)) {
            showWriteOnTagSuccessfulInfo()
        } else {
            showWriteOnTagErrorInfo()
        }
        vibrate()
    }

    private fun showWriteOnTagSuccessfulInfo() {
        scanningInfo.text = getString(R.string.write_tag_success)
    }

    private fun showWriteOnTagErrorInfo() {
        scanningInfo.text = getString(R.string.write_tag_error)
    }

    private fun vibrate() {
        val vibrator = requireActivity().getSystemService(VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(500)
        }
    }
}