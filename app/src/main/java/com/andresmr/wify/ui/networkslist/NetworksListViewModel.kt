package com.andresmr.wify.ui.networkslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.andresmr.wify.domain.repository.WifiRepository
import com.andresmr.wify.entity.Wifi

class NetworksListViewModel(repository: WifiRepository) : ViewModel() {

    val wifiNetworks: LiveData<List<Wifi>> = repository.getNetworks()
}