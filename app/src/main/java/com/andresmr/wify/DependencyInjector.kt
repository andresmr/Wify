package com.andresmr.wify

import com.andresmr.wify.CommonInjector.provideGetWifiNetworkInteractor
import com.andresmr.wify.CommonInjector.provideGetWifiNetworkListInteractor
import com.andresmr.wify.ui.networkdetail.NetworkDetailViewModelFactory
import com.andresmr.wify.ui.networkslist.NetworksListViewModelFactory

object DependencyInjector {
    fun provideNetworkDetailViewModelFactory() = NetworkDetailViewModelFactory(
        provideGetWifiNetworkInteractor()
    )

    fun provideNetworksListViewModelFactory() = NetworksListViewModelFactory(
        provideGetWifiNetworkListInteractor()
    )
}