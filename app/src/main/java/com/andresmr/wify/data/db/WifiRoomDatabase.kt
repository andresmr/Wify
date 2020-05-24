package com.andresmr.wify.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.andresmr.wify.data.dao.WifiDao
import com.andresmr.wify.entity.Wifi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Wifi::class], version = 1)
abstract class WifiRoomDatabase : RoomDatabase() {

    abstract fun wifiDao(): WifiDao

    companion object {
        @Volatile
        private var INSTANCE: WifiRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): WifiRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WifiRoomDatabase::class.java,
                    "wifi_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(WifiDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class WifiDatabaseCallback(private val scope: CoroutineScope) :
            RoomDatabase.Callback() {
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.wifiDao())
                    }
                }
            }
        }

        fun populateDatabase(wifiDao: WifiDao) {
            wifiDao.deleteAll()

            wifiDao.insert(Wifi("ONO795F-5G", "12345", "WPA2_PSK"))
            wifiDao.insert(Wifi("Test wifi", "12345", "WPA2_PSK"))
        }
    }
}