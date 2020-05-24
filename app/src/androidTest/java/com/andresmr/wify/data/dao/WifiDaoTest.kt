package com.andresmr.wify.data.dao

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import com.andresmr.wify.data.db.WifiRoomDatabase
import com.andresmr.wify.entity.Wifi
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
    private lateinit var db: WifiRoomDatabase

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, WifiRoomDatabase::class.java)
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
        val wifi = Wifi("wifi", "1234", "")
        wifiDao.insert(wifi)
        val allNetworks = wifiDao.getAlphabetizedWifis().waitForValue()
        assertEquals(allNetworks[0].ssid, wifi.ssid)
    }

    @Test
    @Throws(Exception::class)
    fun getAllNetworks() {
        val wifi = Wifi("aaa", "1234", "")
        wifiDao.insert(wifi)
        val wifi2 = Wifi("bbb", "1234", "")
        wifiDao.insert(wifi2)
        val allNetworks = wifiDao.getAlphabetizedWifis().waitForValue()
        assertEquals(allNetworks[0].ssid, wifi.ssid)
        assertEquals(allNetworks[1].ssid, wifi2.ssid)
    }

    @Test
    @Throws(Exception::class)
    fun deleteAll() {
        val wifi = Wifi("ccc", "1234", "")
        wifiDao.insert(wifi)
        val wifi2 = Wifi("dddd", "1234", "")
        wifiDao.insert(wifi2)
        wifiDao.deleteAll()
        val allNetworks = wifiDao.getAlphabetizedWifis().waitForValue()
        assertTrue(allNetworks.isEmpty())
    }
}