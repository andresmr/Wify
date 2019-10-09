package com.andresmr.wify.domain.interactor

import com.andresmr.wify.domain.repository.WifiNetworkRepository

class GetWifiNetworkInteractor(private val wifiNetworkRepository: WifiNetworkRepository) {

    fun execute(ssid: String) = wifiNetworkRepository.provideWifiNetworkBySsid(ssid)
}