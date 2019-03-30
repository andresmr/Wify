package com.andresmr.wify

import com.andresmr.wify.domain.GetWifiNetworkInteractor
import com.andresmr.wify.ui.networkdetail.NetworkDetailViewModelFactory

object DependencyInjector {
    fun provideNetworkDetailViewModelFactory() = NetworkDetailViewModelFactory(GetWifiNetworkInteractor())
}