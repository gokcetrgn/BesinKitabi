package com.gokcenaztorgan.besinkitabi.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

// https://raw.githubusercontent.com/atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json

@Entity
data class Besin (
    @ColumnInfo(name="isim")
    @SerializedName("isim")
    val besinIsim : String?,
    @ColumnInfo("kalori")
    val kalori : String?,
    @ColumnInfo("karbonhidrat")
    val karbonhidrat : String?,
    @ColumnInfo("protein")
    val protein : String?,
    @ColumnInfo("yag")
    val yag : String?,
    @ColumnInfo("gorsel")
    val gorsel : String? // var mı yok mu bilmiyorsak
){
    @PrimaryKey(autoGenerate = true)
    var uuid : Int = 0

}
