package com.gokcenaztorgan.besinkitabi.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.gokcenaztorgan.besinkitabi.roomdb.BesinDatabase.Companion.databaseOlustur
import kotlin.concurrent.Volatile

class SpecialSharedPref {

    companion object {
        private val TIME = "time"
        private var sharedPreferences : SharedPreferences? = null
        @Volatile
        private var instance: SpecialSharedPref? = null
        private val lock = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: ozelSharedPrefOlustur(context).also {
                instance = it
            }
        }
        fun ozelSharedPrefOlustur(context: Context) : SpecialSharedPref{
            sharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
            return SpecialSharedPref()
        }

    }
    fun zamaniKaydet(zaman: Long){
        sharedPreferences?.edit()?.putLong(TIME,zaman)?.apply()
    }

    fun zamaniAl() = sharedPreferences?.getLong(TIME,0)
}