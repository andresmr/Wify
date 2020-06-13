package com.andresmr.wify.data.dao

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import com.andresmr.wify.data.db.AppDatabase
import com.andresmr.wify.entity.WifiAuthType
import com.andresmr.wify.entity.WifiNetwork
import com.andresmr.wify.utils.waitForValue
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class WifiDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var wifiDao: WifiDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        wifiDao = db.wifiDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetNetwork() {
        val wifi = WifiNetwork("wifi", "1234", WifiAuthType.WPA2_PSK)
        wifiDao.insert(wifi)
        val allNetworks = wifiDao.getAlphabetizedWifis().waitForValue()
        assertEquals(allNetworks[0].ssid, wifi.ssid)
    }

    @Test
    @Throws(Exception::class)
    fun getAllNetworks() {
        val wifi = WifiNetwork("aaa", "1234", WifiAuthType.WPA2_PSK)
        wifiDao.insert(wifi)
        val wifi2 = WifiNetwork("bbb", "1234", WifiAuthType.WPA2_PSK)
        wifiDao.insert(wifi2)
        val allNetworks = wifiDao.getAlphabetizedWifis().waitForValue()
        assertEquals(allNetworks[0].ssid, wifi.ssid)
        assertEquals(allNetworks[1].ssid, wifi2.ssid)
    }

    @Test
    @Throws(Exception::class)
    fun deleteAll() {
        val wifi = WifiNetwork("ccc", "1234", WifiAuthType.WPA2_PSK)
        wifiDao.insert(wifi)
        val wifi2 = WifiNetwork("dddd", "1234", WifiAuthType.WPA2_PSK)
        wifiDao.insert(wifi2)
        wifiDao.deleteAll()
        val allNetworks = wifiDao.getAlphabetizedWifis().waitForValue()
        assertTrue(allNetworks.isEmpty())
    }
}