package com.andresmr.wify.ui.addnetwork

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.andresmr.wify.R
import com.andresmr.wify.databinding.AddNetworkViewBinding
import com.andresmr.wify.di.Injector
import com.andresmr.wify.entity.WifiAuthType
import com.andresmr.wify.entity.WifiAvailable

class AddNetworkView : Fragment() {

    private val viewModel: AddNetworkViewModel by viewModels {
        Injector.provideAddNetworkViewModelFactory(requireActivity())
    }
    private lateinit var wifiManager: WifiManager
    private lateinit var binding: AddNetworkViewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.add_network_view, container, false)

        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
        binding.availableNetworksList.apply {
            adapter = AvailableNetworksAdapter(viewModel)
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

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
                //TODO: Load scanned wifis on list
                viewModel.setItems(networksAvailable)
            }
        }
        val intentFilter = IntentFilter()
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
        activity?.registerReceiver(wifiScanReceiver, intentFilter)

        checkLocationPermission()
    }

    private fun checkLocationPermission() {
        if (checkSelfPermission(
                requireContext(),
                ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    ACCESS_FINE_LOCATION
                )
            ) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(ACCESS_FINE_LOCATION),
                    MY_PERMISSIONS_REQUEST_LOCATION
                )

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            wifiManager.startScan()
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    wifiManager.startScan()
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
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

    companion object {
        const val MY_PERMISSIONS_REQUEST_LOCATION = 1
    }
}
