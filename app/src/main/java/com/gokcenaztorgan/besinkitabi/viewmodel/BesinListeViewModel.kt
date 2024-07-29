package com.gokcenaztorgan.besinkitabi.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gokcenaztorgan.besinkitabi.model.Besin
import com.gokcenaztorgan.besinkitabi.roomdb.BesinDatabase
import com.gokcenaztorgan.besinkitabi.service.BesinApiServis
import com.gokcenaztorgan.besinkitabi.util.SpecialSharedPref
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BesinListeViewModel(application: Application): AndroidViewModel(application) {
    val besinler = MutableLiveData<List<Besin>>()
    val besinHataMesaji = MutableLiveData<Boolean>()
    val besinYuklenmesi = MutableLiveData<Boolean>()

    private val besinApiServis = BesinApiServis()
    private val ozelSharedPref = SpecialSharedPref(getApplication())


    private val guncellemeZamani = 10 * 60 * 1000 * 1000 * 1000L

    fun veriGuncelle(){
        val kaydedilmeZamani = ozelSharedPref.zamaniAl()
        if(kaydedilmeZamani != null && kaydedilmeZamani != 0L && System.nanoTime() - kaydedilmeZamani < guncellemeZamani){
            // roomdan veri al
            verileriRoomdanAl()
        }else{
            veriInttenAl()
        }
    }
    fun intVerileriGuncelle(){
        veriInttenAl()
    }

    private fun verileriRoomdanAl(){
        besinYuklenmesi.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val besinList = BesinDatabase(getApplication()).besinDao().getAllBesin()
            withContext(Dispatchers.Main){
                besinleriGoster(besinList)
                Toast.makeText(getApplication(),"Besinleri Roomdan Aldık",Toast.LENGTH_LONG).show()
            }
        }

    }
    private fun veriInttenAl(){
        besinYuklenmesi.value = true

        viewModelScope.launch(Dispatchers.IO) {
            val besinList = besinApiServis.getData()
            withContext(Dispatchers.Main){
                besinYuklenmesi.value = false
                //rooma kaydet
                roomaKaydet(besinList)
                Toast.makeText(getApplication(),"Besinleri internetten aldık",Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun besinleriGoster(besinList: List<Besin>){
        besinler.value = besinList
        besinHataMesaji.value = false
        besinYuklenmesi.value = false

    }

    private fun roomaKaydet(besinList : List<Besin>){
        viewModelScope.launch {
            val dao = BesinDatabase(getApplication()).besinDao()
            dao.deleteAllBesin()
            val uuidList = dao.insertAll(*besinList.toTypedArray())
            var i = 0
            while(i < besinList.size)
            {
                besinList[i].uuid = uuidList[i].toInt()
                i = i + 1
            }
            besinleriGoster(besinList)
        }
        ozelSharedPref.zamaniKaydet(System.nanoTime())
    }
}
