package com.andresmr.wify

import com.andresmr.wify.data.WifiNetworkRepositoryImpl
import com.andresmr.wify.domain.interactor.GetWifiNetworkInteractor
import com.andresmr.wify.domain.interactor.GetWifiNetworkListInteractor
import com.andresmr.wify.domain.repository.WifiNetworkRepository
import com.andresmr.wify.ui.networkdetail.NetworkDetailViewModelFactory
import com.andresmr.wify.ui.networkslist.NetworksListViewModelFactory

object DependencyInjector {
    fun provideNetworkDetailViewModelFactory() = NetworkDetailViewModelFactory(
        provideGetWifiNetworkInteractor()
    )

    private fun provideGetWifiNetworkInteractor() =
        GetWifiNetworkInteractor(provideWifiNetworkRepository())

    private fun provideWifiNetworkRepository(): WifiNetworkRepository {
        return WifiNetworkRepositoryImpl()
    }

    private fun provideGetWifiNetworkListInteractor() =
        GetWifiNetworkListInteractor(provideWifiNetworkRepository())

    fun provideNetworksListViewModelFactory() = NetworksListViewModelFactory(
        provideGetWifiNetworkListInteractor()
    )
}