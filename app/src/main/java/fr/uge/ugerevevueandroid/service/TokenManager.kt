import android.content.Context
import android.content.SharedPreferences

class TokenManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("TokenPrefs", Context.MODE_PRIVATE)

    fun saveToken(name: String, token: String) {
        val editor = sharedPreferences.edit()
        editor.putString(name, token)
        editor.apply()
    }

    fun getToken(name: String): String? {
        return sharedPreferences.getString(name, null)
    }

    fun clearToken(name: String) {
        val editor = sharedPreferences.edit()
        editor.remove(name)
        editor.apply()
    }
}