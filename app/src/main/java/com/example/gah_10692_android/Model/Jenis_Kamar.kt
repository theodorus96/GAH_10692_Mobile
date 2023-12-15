package com.example.gah_10692_android.Model

class Jenis_Kamar(
    var rincian_kamar: String? = null,
    var deskripsi: String? = null,
    var ukuran: Int? = null,
    var kapasitas: Int? = null,
    var harga: Int? = null
){
    var id_jenis_kamar: Long? = null
    var jenisKamar: Jenis_Kamar? = null
}