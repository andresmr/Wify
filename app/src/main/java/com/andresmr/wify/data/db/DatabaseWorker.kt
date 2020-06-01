package com.andresmr.wify.data.db

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.andresmr.wify.data.dao.WifiDao
import com.andresmr.wify.entity.WifiAuthType
import com.andresmr.wify.entity.WifiNetwork
import kotlinx.coroutines.coroutineScope

class DatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = coroutineScope {
        val database = AppDatabase.getInstance(applicationContext)
        populateDatabase(database.wifiDao())
        Result.success()
    }

    private fun populateDatabase(wifiDao: WifiDao) {
        wifiDao.deleteAll()
        wifiDao.insert(WifiNetwork("ONO795F-5G", "12345", WifiAuthType.WPA2_PSK))
        wifiDao.insert(WifiNetwork("Test wifi", "12345", WifiAuthType.WPA2_PSK))
    }
}
