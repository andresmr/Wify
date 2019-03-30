package com.andresmr.wify.domain

import com.andresmr.wify.data.WifiNetworksSources
import com.andresmr.wify.entity.WifiNetwork

class GetWifiNetworkInteractor {

    fun execute(ssid: String): WifiNetwork {
        val wifiNetWorksList = WifiNetworksSources().wifiNetworks
        return wifiNetWorksList.first { it.ssid == ssid }
    }
}