package com.andresmr.wify.data.db

import androidx.room.TypeConverter
import com.andresmr.wify.entity.WifiAuthType

class DatabaseConverters {

    @TypeConverter
    fun authTypeToString(authType: WifiAuthType) = authType.name

    @TypeConverter
    fun stringToAuthType(authType: String) = WifiAuthType.valueOf(authType)
}