package com.andresmr.wify.ui.networkdetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.andresmr.wify.data.db.WifiRoomDatabase
import com.andresmr.wify.data.repository.WifiRepositoryImpl
import com.andresmr.wify.domain.repository.WifiRepository

class NetworkDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: WifiRepository

    init {
        val wifiDao = WifiRoomDatabase.getDatabase(application, viewModelScope).wifiDao()
        repository = WifiRepositoryImpl(wifiDao)
    }

    fun getBySsid(ssid: String) = repository.getNetworkBySsid(ssid)
}