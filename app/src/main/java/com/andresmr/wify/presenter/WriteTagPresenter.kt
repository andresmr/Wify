package com.andresmr.wify.presenter

class WriteTagPresenter(private val view: View) {

    fun onResume() {
        view.enableNFCInForeground()
    }

    fun onPause() {
        view.pauseNFCInForeground()
    }

    interface View {
        fun enableNFCInForeground()
        fun pauseNFCInForeground()
    }
}