package com.andresmr.wify.data.repository

import com.andresmr.wify.data.dao.WifiDao
import com.andresmr.wify.domain.repository.WifiRepository
import com.andresmr.wify.entity.Wifi

class WifiRepositoryImpl private constructor(private val wifiDao: WifiDao) : WifiRepository {

    override fun getNetworks() = wifiDao.getAlphabetizedWifis()

    override fun getNetwork(ssid: String) = wifiDao.getById(ssid)

    override suspend fun addNetwork(wifi: Wifi) {
        wifiDao.insert(wifi)
    }

    companion object {
        @Volatile
        private var instance: WifiRepositoryImpl? = null

        fun getInstance(wifiDao: WifiDao) = instance ?: synchronized(this) {
            instance ?: WifiRepositoryImpl(wifiDao).also { instance = it }
        }
    }
}