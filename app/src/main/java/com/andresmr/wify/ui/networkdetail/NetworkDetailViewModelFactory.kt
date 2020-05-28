package com.andresmr.wify.ui.networkdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andresmr.wify.domain.repository.WifiRepository

class NetworkDetailViewModelFactory(
    private val wifiRepository: WifiRepository,
    private val ssid: String
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NetworkDetailViewModel(wifiRepository, ssid) as T
    }
}
