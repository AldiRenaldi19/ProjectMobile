package com.mindmap.mindnotes.sharedpreferences.logindata

import android.content.Context

data class Profile(
    val name: String? = null,
    val alamatEmail: String? = null,
    val kataSandi: String? = null
    // Tambahkan properti lain sesuai kebutuhan aplikasi Anda
    // Misalnya: val nomorTelepon: String? = null, val tanggalLahir: String? = null, dll.
)