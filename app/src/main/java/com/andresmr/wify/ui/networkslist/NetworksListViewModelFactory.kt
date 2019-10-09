package com.andresmr.wify.ui.networkslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andresmr.wify.domain.interactor.GetWifiNetworkListInteractor

@Suppress("UNCHECKED_CAST")
class NetworksListViewModelFactory(private val getWifiNetworkListInteractor: GetWifiNetworkListInteractor) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NetworksListViewModel(getWifiNetworkListInteractor) as T
    }
}