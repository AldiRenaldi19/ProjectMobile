package com.mindmap.mindnotes.sharedpreferences.logindata

data class Profile(
    val name: String? = "MyPrefs",
    val alamatEmail: String? = null,
    val kataSandi: String? = null
    // Tambahkan properti lain sesuai kebutuhan aplikasi Anda
    // Misalnya: val nomorTelepon: String? = null, val tanggalLahir: String? = null, dll.
)