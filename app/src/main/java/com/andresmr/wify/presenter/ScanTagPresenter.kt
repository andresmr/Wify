package com.andresmr.wify.presenter

import com.andresmr.wify.entity.WifiNetwork

class ScanTagPresenter(private val view: View) {

    fun onResume() {
        view.enableNFCInForeground()
    }

    fun onPause() {
        view.pauseNFCInForeground()
    }

    fun onTagRead(wifiNetwork: WifiNetwork) {
        view.vibrate()
        view.showSSID(wifiNetwork.ssid)
        view.showPassword(wifiNetwork.password)
    }

    fun onWriteTagClick() {
        view.openWriteTagView()
    }

    interface View {
        fun enableNFCInForeground()
        fun pauseNFCInForeground()
        fun showSSID(ssidInfo: String)
        fun showPassword(pass: String)
        fun vibrate()
        fun openWriteTagView()
    }
}