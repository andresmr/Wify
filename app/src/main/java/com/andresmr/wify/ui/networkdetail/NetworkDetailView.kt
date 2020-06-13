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
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.andresmr.wify.R
import com.andresmr.wify.databinding.NetworkDetailViewBinding
import com.andresmr.wify.di.Injector

class NetworkDetailView : Fragment() {

    private lateinit var nfcAdapter: NfcAdapter
    private val safeArgs: NetworkDetailViewArgs by navArgs()
    private val viewModel: NetworkDetailViewModel by viewModels {
        Injector.provideNetworkDetailViewModelFactory(requireActivity(), safeArgs.ssid)
    }
    private lateinit var binding: NetworkDetailViewBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.network_detail_view, container, false)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.network.observe(viewLifecycleOwner, Observer {})
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

    fun onTagDetected(tag: Tag) {
        viewModel.writeNetworkOnTag(tag)
        vibrate()
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