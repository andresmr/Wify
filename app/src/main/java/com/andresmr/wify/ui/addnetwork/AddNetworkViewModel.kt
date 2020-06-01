package com.andresmr.wify.ui.addnetwork

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andresmr.wify.domain.repository.WifiRepository
import com.andresmr.wify.entity.WifiNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddNetworkViewModel(private val repository: WifiRepository) : ViewModel() {

    fun addNetwork(wifi: WifiNetwork) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addNetwork(wifi)
        }
    }
}