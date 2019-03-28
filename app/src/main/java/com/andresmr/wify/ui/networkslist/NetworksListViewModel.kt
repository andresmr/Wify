package com.andresmr.wify.ui.networkslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andresmr.wify.entity.WifiAuthType
import com.andresmr.wify.entity.WifiNetwork
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class NetworksListViewModel : ViewModel() {

    private val wifiNetworks: MutableLiveData<List<WifiNetwork>> by lazy {
        MutableLiveData<List<WifiNetwork>>().also {
            loadWifiNetworks()
        }
    }

    fun getWifiNetworks(): LiveData<List<WifiNetwork>> {
        return wifiNetworks
    }

    private fun loadWifiNetworks() {
        // Do an asynchronous operation to fetch wifiNetworks.
        doAsync {
            uiThread {
                wifiNetworks.value = listOf(WifiNetwork("ONO795F-5G", "aVucyb3iGGoBbeRmEYjo9B2B", WifiAuthType.WPA2_PSK))
            }
        }
    }
}
