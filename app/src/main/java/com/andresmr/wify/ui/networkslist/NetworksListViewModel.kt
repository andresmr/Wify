package com.andresmr.wify.ui.networkslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andresmr.wify.domain.repository.WifiRepository
import com.andresmr.wify.entity.WifiNetwork
import com.andresmr.wify.utils.Event

class NetworksListViewModel(repository: WifiRepository) : ViewModel() {

    val items: LiveData<List<WifiNetwork>> = repository.getNetworks()
    private val _navigateToDetails = MutableLiveData<Event<String>>()
    private val _navigateToAddNetwork = MutableLiveData<Event<String>>()

    val navigateToDetails: LiveData<Event<String>>
        get() = _navigateToDetails

    val navigateToAddNetwork: LiveData<Event<String>>
        get() = _navigateToAddNetwork

    fun onNetworkDetail(ssid: String) {
        _navigateToDetails.value =
            Event(ssid)
    }

    fun onAddNetwork() {
        _navigateToAddNetwork.value =
            Event("")
    }
}