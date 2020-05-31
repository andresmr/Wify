package com.andresmr.wify.domain.repository

import androidx.lifecycle.LiveData
import com.andresmr.wify.entity.Wifi

interface WifiRepository {

    fun getNetworks(): LiveData<List<Wifi>>

    fun getNetwork(ssid: String): LiveData<Wifi>

    suspend fun addNetwork(wifi: Wifi)
}