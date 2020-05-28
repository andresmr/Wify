package com.andresmr.wify.data.db

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.andresmr.wify.data.dao.WifiDao
import com.andresmr.wify.entity.Wifi
import kotlinx.coroutines.coroutineScope

class WifiDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = coroutineScope {
        val database = WifiRoomDatabase.getInstance(applicationContext)
        populateDatabase(database.wifiDao())
        Result.success()
    }

    private fun populateDatabase(wifiDao: WifiDao) {
        wifiDao.deleteAll()
        wifiDao.insert(Wifi("ONO795F-5G", "12345", "WPA2_PSK"))
        wifiDao.insert(Wifi("Test wifi", "12345", "WPA2_PSK"))
    }

}
