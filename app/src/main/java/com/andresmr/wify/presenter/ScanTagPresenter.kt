package com.andresmr.wify.presenter

import com.andresmr.wify.entity.Net

class ScanTagPresenter(private val view: View) {

    fun onResume() {
        view.enableNFCInForeground()
    }

    fun onPause() {
        view.pauseNFCInForeground()
    }

    fun onTagRead(net: Net) {
        view.vibrate()
        view.showSSID(net.ssid)
        view.showPassword(net.password)
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