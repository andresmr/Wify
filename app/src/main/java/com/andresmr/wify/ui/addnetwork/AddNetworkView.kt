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
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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

        val adapter = NetworksAvailableAdapter(context) {
            viewModel.add(it)
            Toast.makeText(context, "Wifi added", Toast.LENGTH_LONG).show()
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        viewModel = ViewModelProvider(this).get(AddNetworkViewModel::class.java)

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
                adapter.setWifiAvailableList(networksAvailable)
            }
        }
        val intentFilter = IntentFilter()
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
        activity?.registerReceiver(wifiScanReceiver, intentFilter)
        wifiManager.startScan()

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
