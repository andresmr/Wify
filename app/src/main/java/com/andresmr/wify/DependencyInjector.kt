package com.andresmr.wify

import com.andresmr.wify.CommonInjector.provideGetWifiNetworkInteractor
import com.andresmr.wify.ui.networkdetail.NetworkDetailViewModelFactory

object DependencyInjector {
    fun provideNetworkDetailViewModelFactory() = NetworkDetailViewModelFactory(
        provideGetWifiNetworkInteractor()
    )
}