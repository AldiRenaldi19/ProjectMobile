package com.mindmap.mindnotes.sharedpreferences.registerdata

import android.content.Context

class ProfilePref(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    fun saveProfile(profile: Profile) {
        val editor = sharedPreferences.edit()
        editor.putString("name", profile.namaPanjang)
        editor.putString("nomorTelepon", profile.nomorTelepon)
        editor.putString("alamatEmail", profile.alamatEmail)
        editor.putString("kataSandi", profile.kataSandi)
        editor.putBoolean("isLoggedIn", true)
        editor.apply()
    }
    fun getProfile(): Profile {
        val name = sharedPreferences.getString("name", null)
        val nomorTelepon = sharedPreferences.getString("nomorTelepon", null)
        val alamatEmail = sharedPreferences.getString("alamatEmail", null)
        val kataSandi = sharedPreferences.getString("kataSandi", null)
        return Profile(name, alamatEmail, kataSandi)
    }
    fun clearProfile() {
        val editor = sharedPreferences.edit()
        editor.remove("name")
        editor.remove("nomorTelepon")
        editor.remove("alamatEmail")
        editor.remove("kataSandi")
        editor.clear()
        editor.apply()
    }
    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }
    fun setLoggedIn(isLoggedIn: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLoggedIn", isLoggedIn)
        editor.apply()
    }
}