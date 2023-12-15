package com.example.gah_10692_android.Model

class Transaksi_Layanan (
    var jumlah: Int? = null,
    var subtotal: Int? = null
){
    var id_transaksiLayanan: Long? = null
    var jenisLayanan: Layanan? = null
    var reservasi: Reservasi? = null
}
