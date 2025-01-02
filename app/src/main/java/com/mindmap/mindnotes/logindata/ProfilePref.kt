package com.mindmap.mindnotes.logindata

import android.content.Context

class ProfilePref(context: Context) {

    companion object {
        const val SP_NAME_LOGIN = "profile_pref_login"
        const val KEY_ALAMAT_EMAIL = "alamat_email"
        const val KEY_KATA_SANDI = "kata_sandi"
    }

    private val preference = context.getSharedPreferences(SP_NAME_LOGIN, Context.MODE_PRIVATE)

    fun getProfile(): Profile? {
        val AlamatEmail = preference.getString(KEY_ALAMAT_EMAIL, null)
        val KataSandi = preference.getString(KEY_KATA_SANDI, null)
        val isLoggedIn = preference.getBoolean("isLoggedIn", false)

        return if (AlamatEmail != null && KataSandi != null && isLoggedIn) {
            Profile(AlamatEmail, KataSandi)
        } else {
            null
        }
    }

    fun setProfile(profile: Profile) {

        val prefEditor = preference.edit()
        prefEditor.putString(KEY_ALAMAT_EMAIL, profile.AlamatEmail)
        prefEditor.putString(KEY_KATA_SANDI, profile.KataSandi)
        prefEditor.putBoolean("isLoggedIn", true)
        prefEditor.apply()
    }

    fun clearProfile() {
        val prefEditor = preference.edit()
        prefEditor.clear()
        prefEditor.apply()
    }
}