package com.andresmr.wify.ui.networkdetail

import androidx.lifecycle.LiveData
import com.andresmr.wify.controller.NfcController
import com.andresmr.wify.domain.repository.WifiRepository
import com.andresmr.wify.entity.WifiNetwork
import com.andresmr.wify.utils.mock
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class NetworkDetailViewModelTest {

    private lateinit var viewModel: NetworkDetailViewModel
    private val wifiRepository: WifiRepository = mock()
    private val netWorkLiveData: LiveData<WifiNetwork> = mock()
    private val network: WifiNetwork = mock()
    private val nfcController: NfcController = mock()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        `when`(network.ssid).thenReturn("1234")
        `when`(netWorkLiveData.value).thenReturn(network)
        `when`(wifiRepository.getNetwork(network.ssid)).thenReturn(netWorkLiveData)
        viewModel = NetworkDetailViewModel(wifiRepository, network.ssid, nfcController)
    }

    @Test
    @Throws(InterruptedException::class)
    fun testDefaultValues() {
        Assert.assertEquals(viewModel.network.value, network)
    }
}