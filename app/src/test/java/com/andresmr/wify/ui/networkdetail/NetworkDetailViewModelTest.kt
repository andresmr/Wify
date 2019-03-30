package com.andresmr.wify.ui.networkdetail

import com.andresmr.wify.domain.GetWifiNetworkInteractor
import com.andresmr.wify.entity.WifiNetwork
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class NetworkDetailViewModelTest {

    private lateinit var networkDetailViewModel: NetworkDetailViewModel

    private val getWifiNetworkInteractor: GetWifiNetworkInteractor = Mockito.mock(GetWifiNetworkInteractor::class.java)
    private val wifiNetwork: WifiNetwork = Mockito.mock(WifiNetwork::class.java)


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        networkDetailViewModel = NetworkDetailViewModel(getWifiNetworkInteractor)
    }

    fun shouldLoadWifiNetwork() {
        `when`(getWifiNetworkInteractor.execute(Mockito.anyString())).thenReturn(wifiNetwork)
        assertThat(networkDetailViewModel.getNetwork(), eq(matches(wifiNetwork)))
    }
}