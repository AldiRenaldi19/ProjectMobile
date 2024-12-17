package com.mindmap.mindmaplibrary.sharedpreferences.logindata

data class Profile(
    var AlamatEmail: String? = null,
    var KataSandi: String? = null
    // Tambahkan properti lain sesuai kebutuhan aplikasi Anda
    // Misalnya: val nomorTelepon: String? = null, val tanggalLahir: String? = null, dll.
)