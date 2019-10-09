package com.andresmr.wify.domain.repository

import com.andresmr.wify.entity.WifiNetwork

interface WifiNetworkRepository {

    fun provideWifiNetworkList(): List<WifiNetwork>

    fun provideWifiNetworkBySsid(ssid: String): WifiNetwork
}