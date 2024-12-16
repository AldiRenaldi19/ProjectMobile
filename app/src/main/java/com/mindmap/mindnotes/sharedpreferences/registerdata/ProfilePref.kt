package com.mindmap.mindnotes.sharedpreferences.registerdata

import android.content.Context

class ProfilePref(context: Context) {
    companion object {
        const val SP_NAME_REGISTER = "profile_pref_register"
        const val KEY_NAMA_PANJANG = "nama_panjang"
        const val KEY_NOMOR_TELEPON = "nomor_telepon"
        const val KEY_ALAMAT_EMAIL = "alamat_email"
        const val KEY_KATA_SANDI = "kata_sandi"
    }
    val preference = context.getSharedPreferences(SP_NAME_REGISTER, Context.MODE_PRIVATE)
    fun setProfile(profile: Profile) {
        val prefEditor = preference.edit()
        prefEditor.putString(KEY_NAMA_PANJANG, profile.namaPanjang)
        prefEditor.putString(KEY_NOMOR_TELEPON, profile.nomorTelepon)
        prefEditor.putString(KEY_ALAMAT_EMAIL, profile.alamatEmail)
        prefEditor.putString(KEY_KATA_SANDI, profile.kataSandi)
        prefEditor.apply()
    }
    fun getProfile(): Profile {
       val profile = Profile()
        profile.namaPanjang = preference.getString(KEY_NAMA_PANJANG, null)
        profile.nomorTelepon = preference.getString(KEY_NOMOR_TELEPON, null)
        profile.alamatEmail = preference.getString(KEY_ALAMAT_EMAIL, null)
        profile.kataSandi = preference.getString(KEY_KATA_SANDI, null)
        return profile
    }
}