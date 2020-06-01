package com.andresmr.wify.ui.networkdetail

import android.nfc.Tag
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.andresmr.wify.controller.NfcController
import com.andresmr.wify.domain.repository.WifiRepository
import com.andresmr.wify.entity.WifiNetwork

class NetworkDetailViewModel(
    repository: WifiRepository,
    ssid: String,
    private val nfcController: NfcController
) : ViewModel() {

    val network: LiveData<WifiNetwork> = repository.getNetwork(ssid)

    fun writeNetworkOnTag(tag: Tag): Boolean =
        nfcController.writeNetworkOnTag(network.value!!, tag)
}