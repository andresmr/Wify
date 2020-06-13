package com.andresmr.wify.ui.networkdetail

import android.nfc.Tag
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andresmr.wify.R
import com.andresmr.wify.controller.NfcController
import com.andresmr.wify.domain.repository.WifiRepository
import com.andresmr.wify.entity.WifiNetwork

class NetworkDetailViewModel(
    repository: WifiRepository,
    ssid: String,
    private val nfcController: NfcController
) : ViewModel() {

    val network: LiveData<WifiNetwork> = repository.getNetwork(ssid)
    val scanningInfo: MutableLiveData<Int> = MutableLiveData(R.string.scan_info)

    fun writeNetworkOnTag(tag: Tag): Boolean {
        val result = nfcController.writeNetworkOnTag(network.value!!, tag)
        scanningInfo.value = if (result) R.string.write_tag_success else R.string.write_tag_error
        return result
    }
}