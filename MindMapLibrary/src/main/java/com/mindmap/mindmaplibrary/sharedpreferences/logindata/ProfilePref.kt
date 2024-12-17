package com.mindmap.mindmaplibrary.sharedpreferences.logindata

import android.content.Context

class ProfilePref(context: Context) {
    companion object {
        const val SP_NAME_LOGIN = "profile_pref_login"
        const val KEY_ALAMAT_EMAIL = "alamat_email"
        const val KEY_KATA_SANDI = "kata_sandi"
    }
    val preference = context.getSharedPreferences(SP_NAME_LOGIN, Context.MODE_PRIVATE)
    fun setProfile(profile: Profile) {
        val prefEditor = preference.edit()
        prefEditor.putString(KEY_ALAMAT_EMAIL, profile.AlamatEmail)
        prefEditor.putString(KEY_KATA_SANDI, profile.KataSandi)
        prefEditor.apply()
    }
    fun getProfile(): Profile {
        val profile = Profile()
        profile.AlamatEmail = preference.getString(KEY_ALAMAT_EMAIL, null)
        profile.KataSandi = preference.getString(KEY_KATA_SANDI, null)
        return profile
    }
}