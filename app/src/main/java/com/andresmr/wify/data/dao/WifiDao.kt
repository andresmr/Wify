package com.andresmr.wify.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andresmr.wify.entity.Wifi

@Dao
interface WifiDao {
    @Query("SELECT * from wifi_table ORDER BY ssid ASC")
    fun getAlphabetizedWifis(): LiveData<List<Wifi>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(wifi: Wifi)

    @Query("DELETE FROM wifi_table")
    fun deleteAll()

    @Query("SELECT * FROM wifi_table WHERE ssid =:ssid")
    fun getById(ssid: String): LiveData<Wifi>
}