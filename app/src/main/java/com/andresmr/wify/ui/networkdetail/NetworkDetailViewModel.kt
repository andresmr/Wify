package com.andresmr.wify.ui.networkdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.andresmr.wify.domain.repository.WifiRepository
import com.andresmr.wify.entity.Wifi

class NetworkDetailViewModel(
    repository: WifiRepository,
    ssid: String
) : ViewModel() {

    val network: LiveData<Wifi> = repository.getNetwork(ssid)
}