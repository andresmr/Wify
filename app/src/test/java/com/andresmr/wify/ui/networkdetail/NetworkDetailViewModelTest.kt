package com.andresmr.wify.ui.networkdetail

import com.andresmr.wify.domain.interactor.GetWifiNetworkInteractor
import com.andresmr.wify.entity.WifiNetwork
import org.hamcrest.CoreMatchers.any
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

class NetworkDetailViewModelTest {

    private lateinit var networkDetailViewModel: NetworkDetailViewModel

    private val getWifiNetworkInteractor: GetWifiNetworkInteractor =
        mock(GetWifiNetworkInteractor::class.java)
    private val wifiNetwork: WifiNetwork = mock(WifiNetwork::class.java)


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        networkDetailViewModel = NetworkDetailViewModel(getWifiNetworkInteractor)
    }

    @Test
    fun shouldLoadWifiNetwork() {
        `when`(getWifiNetworkInteractor.execute(anyString())).thenReturn(wifiNetwork)
        networkDetailViewModel.refresh("ssid")
        assertThat(
            networkDetailViewModel.getNetwork().value,
            any(NetworkDetailUiModelWrapper::class.java)
        )
    }
}