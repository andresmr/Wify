package com.andresmr.wify.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.andresmr.wify.data.dao.WifiDao
import com.andresmr.wify.entity.Wifi

@Database(entities = [Wifi::class], version = 1)
abstract class WifiRoomDatabase : RoomDatabase() {

    abstract fun wifiDao(): WifiDao

    companion object {
        private const val DATABASE_NAME = "wifi_database"

        @Volatile
        private var instance: WifiRoomDatabase? = null

        fun getInstance(context: Context): WifiRoomDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): WifiRoomDatabase {
            return Room.databaseBuilder(context, WifiRoomDatabase::class.java, DATABASE_NAME)
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        val request = OneTimeWorkRequestBuilder<WifiDatabaseWorker>().build()
                        WorkManager.getInstance(context).enqueue(request)
                    }
                }).build()
        }
    }
}