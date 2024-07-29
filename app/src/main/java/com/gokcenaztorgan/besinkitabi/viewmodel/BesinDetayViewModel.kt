package com.gokcenaztorgan.besinkitabi.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gokcenaztorgan.besinkitabi.model.Besin
import com.gokcenaztorgan.besinkitabi.roomdb.BesinDatabase
import kotlinx.coroutines.launch

class BesinDetayViewModel(application: Application) : AndroidViewModel(application) {
    val besinLiveData = MutableLiveData<Besin>()

    fun roomVerisiniAl(uuid: Int){
        viewModelScope.launch {
            val dao = BesinDatabase(getApplication()).besinDao()
            val besin = dao.getBesin(uuid)
            besinLiveData.value = besin
        }
    }

}