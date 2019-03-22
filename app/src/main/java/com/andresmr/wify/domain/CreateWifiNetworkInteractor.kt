package com.andresmr.wify.domain

import com.andresmr.wify.entity.WifiNetwork
import com.andresmr.wify.entity.WifiAuthType

class CreateWifiNetworkInteractor {

    fun execute(ssid: String, password: String): WifiNetwork {
        return WifiNetwork(ssid, password, WifiAuthType.WPA2_PSK)
    }
}