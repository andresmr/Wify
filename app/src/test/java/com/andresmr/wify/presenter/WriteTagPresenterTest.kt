package com.andresmr.wify.presenter

import com.andresmr.wify.domain.CreateWifiNetworkInteractor
import com.andresmr.wify.entity.WifiNetwork
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class WriteTagPresenterTest {

    lateinit var presenter: WriteTagPresenter

    private val view: WriteTagPresenter.View = mock(WriteTagPresenter.View::class.java)
    private val createWifiNetworkInteractor: CreateWifiNetworkInteractor = mock(CreateWifiNetworkInteractor::class.java)
    private val wifiNetwork: WifiNetwork = mock(WifiNetwork::class.java)

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = WriteTagPresenter(view, createWifiNetworkInteractor)
    }

    @Test
    fun shouldWriteWifiNetWorkOnTag() {
        `when`(createWifiNetworkInteractor.execute(anyString(), anyString())).thenReturn(wifiNetwork)
        presenter.onTagDetected("ssid", "pwd")
        verify(view).writeNetOnTag(wifiNetwork)
    }

    @Test
    fun shouldEmptySSIDInfo() {
        presenter.onTagDetected("", "pwd")
        verify(view).showErrorOnSSID()
    }

    @Test
    fun shouldEmptyPasswordInfo() {
        presenter.onTagDetected("SSID", "")
        verify(view).showErrorOnPassword()
    }
}