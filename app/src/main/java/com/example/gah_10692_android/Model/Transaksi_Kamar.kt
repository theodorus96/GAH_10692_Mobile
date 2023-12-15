package com.example.gah_10692_android.Model

class Transaksi_Kamar (
    var jumlah: Int? = null,
    var subtotal: Int? = null
){
    var id_transaksiKamar: Long? = null
    var jenisKamar: Jenis_Kamar? = null
    var reservasi: Reservasi? = null
}