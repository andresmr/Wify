package com.andresmr.wify

import com.andresmr.wify.data.WifiNetworkRepositoryImpl
import com.andresmr.wify.domain.interactor.GetWifiNetworkInteractor
import com.andresmr.wify.domain.interactor.GetWifiNetworkListInteractor
import com.andresmr.wify.domain.repository.WifiNetworkRepository

object CommonInjector {
    fun provideGetWifiNetworkInteractor() =
        GetWifiNetworkInteractor(provideWifiNetworkRepository())

    private fun provideWifiNetworkRepository(): WifiNetworkRepository {
        return WifiNetworkRepositoryImpl()
    }

    fun provideGetWifiNetworkListInteractor() =
        GetWifiNetworkListInteractor(provideWifiNetworkRepository())
}