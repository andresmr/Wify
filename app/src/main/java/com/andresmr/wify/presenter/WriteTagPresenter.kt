package com.andresmr.wify.presenter

import com.andresmr.wify.domain.CreateWifiNetworkInteractor
import com.andresmr.wify.entity.WifiNetwork

class WriteTagPresenter(private val view: View,
                        private val createWifiNetworkInteractor: CreateWifiNetworkInteractor) {

    init {
        view.showScanInfo()
    }

    fun onResume() {
        view.enableNFCInForeground()
    }

    fun onPause() {
        view.pauseNFCInForeground()
        view.hideScanInfo()
    }

    fun onTagDetected(ssid: String, password: String) {
        when {
            ssid.isEmpty() -> view.showErrorOnSSID()
            password.isEmpty() -> view.showErrorOnPassword()
            else -> {
                val net = createWifiNetworkInteractor.execute(ssid, password)
                view.writeNetOnTag(net)
            }
        }
    }

    fun onWriteOnTagSuccessful() {
        view.vibrate()
        view.showWriteOnTagSuccessfulInfo()
    }

    fun onWriteOnTagError() {
        view.vibrate()
        view.showWriteOnTagErrorInfo()
    }

    interface View {
        fun enableNFCInForeground()
        fun pauseNFCInForeground()
        fun showScanInfo()
        fun hideScanInfo()
        fun showErrorOnSSID()
        fun showErrorOnPassword()
        fun writeNetOnTag(wifiNetwork: WifiNetwork)
        fun showWriteOnTagSuccessfulInfo()
        fun showWriteOnTagErrorInfo()
        fun vibrate()
    }
}