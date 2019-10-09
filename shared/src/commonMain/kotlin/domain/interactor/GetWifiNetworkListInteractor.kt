package com.andresmr.wify.domain.interactor

import com.andresmr.wify.domain.repository.WifiNetworkRepository

class GetWifiNetworkListInteractor(private val wifiNetworkRepository: WifiNetworkRepository) {

    fun execute() = wifiNetworkRepository.provideWifiNetworkList()
}