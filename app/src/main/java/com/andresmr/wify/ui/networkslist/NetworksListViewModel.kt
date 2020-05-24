package com.andresmr.wify.ui.networkslist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.andresmr.wify.data.db.WifiRoomDatabase
import com.andresmr.wify.data.repository.WifiRepositoryImpl
import com.andresmr.wify.domain.repository.WifiRepository
import com.andresmr.wify.entity.Wifi

class NetworksListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: WifiRepository

    val wifiNetworks: LiveData<List<Wifi>>

    init {
        val wifiDao = WifiRoomDatabase.getDatabase(application, viewModelScope).wifiDao()
        repository = WifiRepositoryImpl(wifiDao)
        wifiNetworks = repository.provideWifiNetworkList()
    }
}