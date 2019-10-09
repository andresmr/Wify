package com.andresmr.wify.entity

data class WifiNetwork(val ssid: String, val password: String, val authType: WifiAuthType)

enum class WifiAuthType {
    OPEN, WEP, WPA_PSK, WPA_EAP, WPA2_EAP, WPA2_PSK
}