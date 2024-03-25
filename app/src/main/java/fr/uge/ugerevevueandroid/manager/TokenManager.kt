import android.content.Context
import android.content.SharedPreferences
import com.fasterxml.jackson.annotation.JsonProperty
import org.apache.commons.codec.binary.Base64
import com.fasterxml.jackson.databind.ObjectMapper
import fr.uge.ugerevevueandroid.information.AuthInformation


class TokenManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("TokenPrefs", Context.MODE_PRIVATE)
    data class JwtPayload(
        @JsonProperty("exp") val exp: Long,
        @JsonProperty("sub") val sub: String,
        @JsonProperty("role") val role: RoleData
    )

    data class RoleData(
        @JsonProperty("id") val id: Int,
        @JsonProperty("typeRole") val typeRole: String
    )

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

    fun hasBearer(): Boolean{
        return getToken("bearer") != null
    }

    fun getAuth() : AuthInformation? {
        val jwt = getToken("bearer")
        if (jwt != null){
            val parts = jwt.split('.')
            val payload = String(Base64.decodeBase64(parts[1]))
            val objectMapper = ObjectMapper()
            val result = objectMapper.readValue(payload, JwtPayload::class.java)
            return AuthInformation(result.sub, result.role.typeRole)
        }
        return null
    }
}