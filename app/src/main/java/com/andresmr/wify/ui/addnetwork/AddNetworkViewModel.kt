package com.andresmr.wify.ui.addnetwork

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.andresmr.wify.data.db.WifiRoomDatabase
import com.andresmr.wify.data.repository.WifiRepositoryImpl
import com.andresmr.wify.domain.repository.WifiRepository
import com.andresmr.wify.entity.Wifi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddNetworkViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: WifiRepository

    init {
        val wifiDao = WifiRoomDatabase.getDatabase(application, viewModelScope).wifiDao()
        repository = WifiRepositoryImpl(wifiDao)
    }

    fun add(wifi: Wifi) = viewModelScope.launch(Dispatchers.IO) {
        repository.addWifiNetwork(wifi)
    }
}
