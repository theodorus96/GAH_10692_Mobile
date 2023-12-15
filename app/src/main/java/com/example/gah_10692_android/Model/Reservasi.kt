package com.example.gah_10692_android.Model

class Reservasi (
    var id_booking: String? = null,
    var tanggal_checkin: String? = null,
    var tanggal_checkout: String? = null,
    var jumlah_dewasa: Int? = null,
    var jumlah_anak: Int? = null,
    var tanggal_reservasi: String? = null,
    var tanggal_pembayaran: String? = null,
    var tanggal_cetak: String? = null,
    var total_harga: Int? = null,
    var jenis_tamu: String? = null,
    var status: String? = null,
    var nomor_rekening: Int? = null,
    var bukti_pembayaran: String? = null,
    var permintaan: String? = null
){
    var id_reservasi: Long? = null
    var data_user: dataUser? = null
    var pegawai: Pegawai? = null
}