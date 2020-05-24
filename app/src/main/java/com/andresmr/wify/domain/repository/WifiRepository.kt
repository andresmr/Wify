package com.andresmr.wify.domain.repository

import androidx.lifecycle.LiveData
import com.andresmr.wify.entity.Wifi

interface WifiRepository {

    fun provideWifiNetworkList(): LiveData<List<Wifi>>

    suspend fun addWifiNetwork(wifi: Wifi)

    fun getNetworkBySsid(ssid: String): LiveData<Wifi>
}