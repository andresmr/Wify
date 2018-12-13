package com.andresmr.wify.presenter

import com.andresmr.wify.domain.CreateNetInteractor
import com.andresmr.wify.entity.Net

class WriteTagPresenter(private val view: View,
                        private val createNetInteractor: CreateNetInteractor) {

    init {
        view.setListeners()
    }

    fun onPause() {
        view.pauseNFCInForeground()
        view.hideScanInfo()
    }

    fun onWriteTagClick(ssid: String, password: String) {
        when {
            ssid.isEmpty() -> view.showErrorOnSSID()
            password.isEmpty() -> view.showErrorOnPassword()
            else -> {
                view.enableNFCInForeground()
                view.showScanInfo()
            }
        }
    }

    fun onTagDetected(ssid: String, password: String) {
        val net = createNetInteractor.execute(ssid, password)
        view.writeNetOnTag(net)
    }

    fun onWriteOnTagSuccessful() {
        view.vibrate()
        view.showWriteOnTagSuccessfulInfo()
        view.pauseNFCInForeground()
    }

    fun onWriteOnTagError() {
        view.vibrate()
        view.showWriteOnTagErrorInfo()
        view.pauseNFCInForeground()
    }

    interface View {
        fun setListeners()
        fun enableNFCInForeground()
        fun pauseNFCInForeground()
        fun showScanInfo()
        fun hideScanInfo()
        fun showErrorOnSSID()
        fun showErrorOnPassword()
        fun writeNetOnTag(net: Net)
        fun showWriteOnTagSuccessfulInfo()
        fun showWriteOnTagErrorInfo()
        fun vibrate()
    }
}