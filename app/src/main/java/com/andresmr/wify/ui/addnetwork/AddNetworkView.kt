package com.andresmr.wify.ui.addnetwork

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.andresmr.wify.R
import com.andresmr.wify.entity.WifiAuthType
import com.andresmr.wify.entity.WifiAvailable
import kotlinx.android.synthetic.main.add_network_view.*

class AddNetworkView : Fragment() {

    private lateinit var viewModel: AddNetworkViewModel
    private lateinit var wifiManager: WifiManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.add_network_view, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerView.layoutManager = LinearLayoutManager(context)
        viewModel = createViewModel()
        wifiManager =
            activity?.applicationContext?.getSystemService(Context.WIFI_SERVICE) as WifiManager

        val wifiScanReceiver = object : BroadcastReceiver() {

            override fun onReceive(context: Context, intent: Intent) {
                val wifiList = wifiManager.scanResults
                val netCount = wifiList.size
                Log.d("Wifi", "Total Wifi Network$netCount")
                val networksAvailable = mutableListOf<WifiAvailable>()
                wifiList.forEach {
                    networksAvailable.add(getWifiAvailable(it))
                }
                val adapter = NetworksAvailableAdapter(networksAvailable) {}
                recyclerView.adapter = adapter
                adapter.notifyDataSetChanged()
            }
        }
        val intentFilter = IntentFilter()
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
        activity?.registerReceiver(wifiScanReceiver, intentFilter)
        wifiManager.startScan()

    }

    private fun createViewModel(): AddNetworkViewModel {
        return ViewModelProviders.of(this).get(AddNetworkViewModel::class.java)
    }

    private fun getWifiAvailable(scanResult: ScanResult): WifiAvailable = WifiAvailable(
        scanResult.SSID,
        with(scanResult.capabilities) {
            when {
                contains("WEP") -> WifiAuthType.WEP
                contains("PSK") -> WifiAuthType.WPA2_PSK
                contains("EAP") -> WifiAuthType.WPA2_EAP
                else -> WifiAuthType.OPEN
            }
        }
    )
}
