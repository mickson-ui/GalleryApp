import android.content.Context
import android.content.SharedPreferences
import com.example.galleryapp.utils.navigation.TokenResponse
import com.google.gson.Gson

class SessionManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("session", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()
    private val gson = Gson()

    companion object {
        private const val KEY_TOKEN = "token"
        private const val KEY_EMAIL = "email"
        private const val KEY_USERID = "userID"
    }

    var token: String?
        get() = sharedPreferences.getString(KEY_TOKEN, null)
        set(value) {
            editor.putString(KEY_TOKEN, value).apply()
        }

    var userId: String?
        get() = sharedPreferences.getString(KEY_USERID, null)
        set(value) {
            editor.putString(KEY_USERID, value).apply()
        }

    var email: String?
        get() = sharedPreferences.getString(KEY_EMAIL, null)
        set(value) {
            editor.putString(KEY_EMAIL, value).apply()
        }
    val isTokenSaved: Boolean
        get() = sharedPreferences.contains(KEY_TOKEN)
    fun clearSession() {
        editor.clear().apply()
    }
}
