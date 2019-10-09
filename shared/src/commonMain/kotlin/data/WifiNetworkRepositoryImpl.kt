package com.andresmr.wify.data

import com.andresmr.wify.domain.repository.WifiNetworkRepository
import com.andresmr.wify.entity.WifiAuthType
import com.andresmr.wify.entity.WifiNetwork

class WifiNetworkRepositoryImpl : WifiNetworkRepository {

    private val wifiNetworks: List<WifiNetwork> =
        listOf(
            WifiNetwork("ONO795F-5G", "12345", WifiAuthType.WPA2_PSK),
            WifiNetwork("ONO795F", "12345", WifiAuthType.WPA2_PSK)
        )

    override fun provideWifiNetworkList() = wifiNetworks

    override fun provideWifiNetworkBySsid(ssid: String) = wifiNetworks.first { it.ssid == ssid }
}