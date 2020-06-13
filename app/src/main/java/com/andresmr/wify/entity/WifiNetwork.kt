package com.andresmr.wify.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wifi_table")
data class WifiNetwork(
    @PrimaryKey
    @ColumnInfo(name = "ssid") val ssid: String,
    @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name = "auth_type") val authType: WifiAuthType
)