package com.andresmr.wify.ui.addnetwork

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiManager
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.andresmr.wify.R

class AddNetworkView : Fragment() {

    private lateinit var viewModel: AddNetworkViewModel
    private lateinit var wifiManager: WifiManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_network_view, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AddNetworkViewModel::class.java)
        // TODO: Use the ViewModel

        wifiManager = activity?.applicationContext?.getSystemService(Context.WIFI_SERVICE) as WifiManager

        val wifiScanReceiver = object : BroadcastReceiver() {

            override fun onReceive(context: Context, intent: Intent) {
                val wifiList= wifiManager.scanResults
                val netCount=wifiList.size
                Log.d("Wifi", "Total Wifi Network$netCount")
            }
        }
        val intentFilter = IntentFilter()
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
        activity?.registerReceiver(wifiScanReceiver, intentFilter)

        wifiManager.startScan()

    }
}
