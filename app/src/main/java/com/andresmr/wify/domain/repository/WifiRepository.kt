package com.andresmr.wify.domain.repository

import androidx.lifecycle.LiveData
import com.andresmr.wify.entity.WifiNetwork

interface WifiRepository {

    fun getNetworks(): LiveData<List<WifiNetwork>>

    fun getNetwork(ssid: String): LiveData<WifiNetwork>

    suspend fun addNetwork(wifi: WifiNetwork)
}