package id.finalproject.binar.secondhand.helper

import android.content.Context

class SharedPreferences(context: Context) {
    private val sharedPref = context.getSharedPreferences(PREFERENCES, 0)
    private val editor = sharedPref.edit()

    suspend fun saveToken(value: String) {
        editor.apply {
            putString(ACCESS_TOKEN, value)
            apply()
        }
    }

    suspend fun saveStatusLogin() {
        editor.apply {
            putBoolean(LOGIN_KEY, true)
            apply()
        }
    }

    suspend fun saveEmail(value: String) {
        editor.apply {
            putString(EMAIL_KEY, value)
            apply()
        }
    }

    fun getToken() = sharedPref.getString(ACCESS_TOKEN, "")
    fun getLogin() = sharedPref.getBoolean(LOGIN_KEY, false)
    fun getEmail() = sharedPref.getString(EMAIL_KEY, "")

    fun sessionDelete() {
        editor.apply {
            clear()
            apply()
        }
    }

    companion object {
        private const val PREFERENCES = "pref"
        const val LOGIN_KEY = "login"
        const val ACCESS_TOKEN = "access_token"
        const val EMAIL_KEY = "email"
    }
}