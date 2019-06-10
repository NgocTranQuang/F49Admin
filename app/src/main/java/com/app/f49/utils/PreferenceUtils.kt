package com.app.f49.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import java.util.*

class PreferenceUtils(context: Context) {

    companion object {
        const val KEY_LANGUAGE = "vn.f49.app.utils.prefs.KEY_LANGUAGE"
        const val KEY_LANGUAGE_ID = "vn.f49.app.utils.prefs.KEY_LANGUAGE_ID"

        /**
         * Login response
         */
        const val KEY_TOKEN = "vn.f49.app.prefs.KEY_TOKEN"
        const val KEY_IS_LOGOUT = "vn.f49.app.prefs.KEY_IS_LOGOUT"
        const val KEY_TOKEN_FIREBASE = "vn.f49.app.prefs.KEY_TOKEN_FIREBASE"
        const val KEY_FULL_NAME = "vn.f49.app.prefs.KEY_FULL_NAME"
        const val KEY_AVATAR = "vn.f49.app.prefs.KEY_AVATAR"
        const val KEY_GENDER = "vn.f49.app.prefs.KEY_GENDER"
        const val KEY_EMAIL = "vn.f49.app.prefs.KEY_EMAIL"
        const val KEY_PASSWORD = "vn.f49.app.prefs.KEY_PASSWORD"
        const val KEY_REMEMBER_LOGIN = "vn.f49.app.prefs.KEY_REMEMBER_LOGIN"
        const val KEY_PHONE_NUMBER = "vn.f49.app.prefs.KEY_PHONE_NUMBER"

        const val KEY_NOTIFICATION_LIST = "vn.f49.app.utils.prefs.KEY_NOTIFICATION_LIST"
        private val TAG = "PreferenceUtil"
        private var instance: PreferenceUtils? = null

        fun newInstance(context: Context): PreferenceUtils {
            if (instance == null) {
                instance = PreferenceUtils(context)
            }
            return instance as PreferenceUtils
        }

        fun getBoolean(con: Context, key: String, defValue: Boolean): Boolean {
            try {
                return pref(con).getBoolean(key, defValue)
            } catch (e: Exception) {
                return defValue
            }

        }

        fun getInt(con: Context, key: String, defValue: Int): Int {
            try {
                return pref(con).getInt(key, defValue)
            } catch (e: Exception) {
                return defValue
            }

        }

        fun getString(con: Context, key: String, defValue: String?): String? {
            try {
                return pref(con).getString(key, defValue)
            } catch (e: Exception) {
                return defValue
            }

        }

        fun getLong(con: Context, key: String, defValue: Long): Long {
            try {
                return pref(con).getLong(key, defValue)
            } catch (e: Exception) {
                return defValue
            }

        }

        fun getFloat(con: Context, key: String, defValue: Float): Float {
            try {
                return pref(con).getFloat(key, defValue)
            } catch (e: Exception) {
                return defValue
            }

        }

        fun getStringSet(con: Context, key: String, defValue: Set<String>): Set<String>? {
            try {
                var ret: HashSet<String>? = null
                val set = pref(con).getStringSet(key, null)
                if (set != null) {
                    ret = HashSet(set)
                }
                return ret
            } catch (e: Exception) {
                return defValue
            }

        }

        fun writeString(con: Context, key: String, value: String?) {
            try {
                pref(con).edit().putString(key, value).commit()
            } catch (e: Exception) {
            }

        }

        fun writeBoolean(con: Context, key: String, value: Boolean) {
            try {
                pref(con).edit().putBoolean(key, value).commit()
            } catch (e: Exception) {
            }

        }

        fun writeInt(con: Context, key: String, value: Int) {
            try {
                pref(con).edit().putInt(key, value).commit()
            } catch (e: Exception) {
            }

        }

        fun writeLong(con: Context, key: String, value: Long) {
            try {
                pref(con).edit().putLong(key, value).commit()
            } catch (e: Exception) {
            }

        }

        fun writeFloat(con: Context, key: String, value: Float) {
            try {
                pref(con).edit().putFloat(key, value).commit()
            } catch (e: Exception) {
            }

        }

        fun writeSet(con: Context, key: String, set: Set<String>) {
            try {
                pref(con).edit().putStringSet(key, set).commit()
            } catch (e: Exception) {
            }

        }

        fun remove(con: Context, key: String) {
            try {
                pref(con).edit().remove(key).commit()
            } catch (e: Exception) {
            }

        }

        private fun pref(con: Context): SharedPreferences {
            return PreferenceManager.getDefaultSharedPreferences(con)
        }
    }
}
