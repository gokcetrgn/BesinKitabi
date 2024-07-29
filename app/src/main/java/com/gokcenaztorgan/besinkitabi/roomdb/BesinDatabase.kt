package com.gokcenaztorgan.besinkitabi.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gokcenaztorgan.besinkitabi.model.Besin

@Database(entities = [Besin::class],version = 1)
abstract class BesinDatabase : RoomDatabase() {

    abstract fun besinDao(): BesinDao

    companion object{
        @Volatile
        private var instance : BesinDatabase? = null
        private val lock = Any()
        operator fun invoke(context : Context) = instance ?: synchronized(lock){
            instance ?: databaseOlustur(context).also {
                instance = it
            }
        }
        fun databaseOlustur(context: Context) = Room.databaseBuilder(
            context.applicationContext,BesinDatabase::class.java,"besindatabase"
        ).build()
    }
}