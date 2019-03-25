package com.andresmr.wify.entity

import com.andresmr.wify.BuildConfig

data class WifiNetwork(val ssid: String, val password: String, val authType: WifiAuthType) {
    companion object {
        const val PACKAGE_NAME = BuildConfig.APPLICATION_ID
        const val NFC_TOKEN_MIME_TYPE = "application/vnd.wfa.wsc"

        const val AUTH_TYPE_OPEN: Short = 0x0001
        const val AUTH_TYPE_WPA_PSK: Short = 0x0002
        const val AUTH_TYPE_WPA_EAP: Short = 0x0008
        const val AUTH_TYPE_WPA2_EAP: Short = 0x0010
        const val AUTH_TYPE_WPA2_PSK: Short = 0x0020

        const val CREDENTIAL_FIELD_ID: Short = 0x100e
        const val SSID_FIELD_ID: Short = 0x1045
        const val AUTH_TYPE_FIELD_ID: Short = 0x1003
        const val NETWORK_KEY_FIELD_ID: Short = 0x1027

        const val MAX_MAC_ADDRESS_SIZE_BYTES = 6
        const val MAX_NETWORK_KEY_SIZE_BYTES = 64
    }
}

enum class WifiAuthType {
    OPEN, WEP, WPA_PSK, WPA_EAP, WPA2_EAP, WPA2_PSK
}