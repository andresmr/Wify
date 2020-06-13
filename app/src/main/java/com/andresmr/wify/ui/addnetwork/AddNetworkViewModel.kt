package com.andresmr.wify.ui.addnetwork

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andresmr.wify.domain.repository.WifiRepository
import com.andresmr.wify.entity.WifiAvailable
import com.andresmr.wify.entity.WifiNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddNetworkViewModel(private val repository: WifiRepository) : ViewModel() {

    var items: MutableLiveData<List<WifiAvailable>> = MutableLiveData()

    fun setItems(items: List<WifiAvailable>) {
        this.items.value = items
    }

    fun addNetwork(wifi: WifiAvailable) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addNetwork(WifiNetwork(wifi.ssid, "123", wifi.authType))
        }
        //TODO: Give feedback to the user network has been added
    }
}