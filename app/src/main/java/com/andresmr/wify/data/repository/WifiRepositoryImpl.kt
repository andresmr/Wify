package com.andresmr.wify.data.repository

import androidx.lifecycle.LiveData
import com.andresmr.wify.data.dao.WifiDao
import com.andresmr.wify.entity.Wifi
import com.andresmr.wify.domain.repository.WifiRepository

class WifiRepositoryImpl(private val wifiDao: WifiDao) : WifiRepository {
    override fun provideWifiNetworkList() = wifiDao.getAlphabetizedWifis()

    override suspend fun addWifiNetwork(wifi: Wifi) {
        wifiDao.insert(wifi)
    }

    override fun getNetworkBySsid(ssid: String) = wifiDao.getById(ssid)
}