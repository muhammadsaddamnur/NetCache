package saddam.com.netcache.sharepreference

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

class SharedPreference(context: Context) {
    private val s = "kotlincodes"
    private val sharedPref: SharedPreferences = context.getSharedPreferences(s, Context.MODE_PRIVATE)


    @SuppressLint("CommitPrefEdits")
    fun save(KEY_NAME: String, text: String) {

        val editor: SharedPreferences.Editor = sharedPref.edit()

        editor.putString(KEY_NAME, text)

        editor.apply()
    }

    fun getValueString(KEY_NAME: String): String? {

        return sharedPref.getString(KEY_NAME, null)


    }
}