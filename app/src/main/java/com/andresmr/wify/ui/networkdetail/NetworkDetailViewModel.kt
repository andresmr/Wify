package com.andresmr.wify.ui.networkdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andresmr.wify.domain.GetWifiNetworkInteractor

class NetworkDetailViewModel(private val getWifiNetworkInteractor: GetWifiNetworkInteractor) :
    ViewModel() {

    private val wifiNetwork: MutableLiveData<NetworkDetailUiModelWrapper> = MutableLiveData()

    fun getNetwork() = wifiNetwork

    fun refresh(ssid: String) {
        wifiNetwork.value =
            NetworkDetailUiModelWrapper.success(getWifiNetworkInteractor.execute(ssid))
    }
}