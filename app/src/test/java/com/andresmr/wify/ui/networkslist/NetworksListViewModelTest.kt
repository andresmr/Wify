package com.andresmr.wify.ui.networkslist

import androidx.lifecycle.LiveData
import com.andresmr.wify.domain.repository.WifiRepository
import com.andresmr.wify.entity.WifiNetwork
import com.andresmr.wify.utils.mock
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class NetworksListViewModelTest {

    private lateinit var viewModel: NetworksListViewModel
    private val wifiRepository: WifiRepository = mock()
    private val networkListLiveData: LiveData<List<WifiNetwork>> = mock()
    private val networkList: List<WifiNetwork> = mock()


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        `when`(networkListLiveData.value).thenReturn(networkList)
        `when`(wifiRepository.getNetworks()).thenReturn(networkListLiveData)
        viewModel = NetworksListViewModel(wifiRepository)
    }

    @Test
    @Throws(InterruptedException::class)
    fun testDefaultValues() {
        Assert.assertEquals(viewModel.items.value, networkList)
    }
}