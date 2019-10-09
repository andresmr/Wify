package com.andresmr.wify.ui.networkdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andresmr.wify.domain.interactor.GetWifiNetworkInteractor

@Suppress("UNCHECKED_CAST")
class NetworkDetailViewModelFactory(private val getWifiNetworkInteractor: GetWifiNetworkInteractor) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NetworkDetailViewModel(getWifiNetworkInteractor) as T
    }
}