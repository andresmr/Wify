package com.andresmr.wify.ui.networkdetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class NetworkDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val wifiNetwork: MutableLiveData<NetworkDetailUiModelWrapper> = MutableLiveData()

    fun getNetwork() = wifiNetwork

    fun refresh(ssid: String) {
        /*wifiNetwork.value =
            NetworkDetailUiModelWrapper.success(getWifiNetworkInteractor.execute(ssid))*/
    }
}