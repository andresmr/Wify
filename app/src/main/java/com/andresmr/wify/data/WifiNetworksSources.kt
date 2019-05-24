package com.andresmr.wify.data

import com.andresmr.wify.entity.WifiAuthType
import com.andresmr.wify.entity.WifiNetwork

data class WifiNetworksSources(
        val wifiNetworks: List<WifiNetwork> =
                listOf(
                        WifiNetwork("ONO795F-5G", "aVucyb3iGGoBbeRmEYjo9B2B", WifiAuthType.WPA2_PSK),
                        WifiNetwork("ONO795F", "aVucyb3iGGoBbeRmEYjo9B2B", WifiAuthType.WPA2_PSK)
                )
)