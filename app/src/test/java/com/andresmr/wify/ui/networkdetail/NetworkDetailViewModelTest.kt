package com.andresmr.wify.ui.networkdetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.andresmr.wify.data.db.WifiRoomDatabase
import com.andresmr.wify.data.repository.WifiRepositoryImpl
import com.andresmr.wify.domain.repository.WifiRepository
import org.junit.Before
import org.junit.Rule

class NetworkDetailViewModelTest {

    private lateinit var wifiRoomDatabase: WifiRoomDatabase
    private lateinit var viewModel: NetworkDetailViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        wifiRoomDatabase =
            Room.inMemoryDatabaseBuilder(context, WifiRoomDatabase::class.java).build()

        val repository: WifiRepository = WifiRepositoryImpl(wifiRoomDatabase.wifiDao())
        //viewModel = NetworkDetailViewModel()
    }
}