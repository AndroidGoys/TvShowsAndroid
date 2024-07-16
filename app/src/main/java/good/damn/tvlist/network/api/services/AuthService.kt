package good.damn.tvlist.network.api.services

import android.util.Log
import good.damn.tvlist.App
import good.damn.tvlist.network.NetworkJSONService
import good.damn.tvlist.network.api.models.auth.ModelRegister
import org.json.JSONObject
import java.net.URL

class AuthService
: NetworkJSONService(
    App.CACHE_DIR
) {

    companion object {
        private const val TAG = "AuthService"
        private const val URL_REGISTER = "${App.URL}/api/auth/register"
        private const val JSON_TYPE = "application/json"
    }

    fun registerUser(
        email: String,
        password: String,
        username: String
    ): ModelRegister? {

        val json = JSONObject()

        json.put(
            "username",
            username
        )

        json.put(
            "email",
            email
        )

        json.put(
            "password",
            password
        )

        val response = postJSON(
            URL_REGISTER,
            json,
            JSON_TYPE
        )

        Log.d(TAG, "registerUser: RESPONSE: $response")
        if (response == null) {
            return null
        }

        val jsonString = response.string()
        Log.d(TAG, "registerUser: RESPONSE_JSON: $jsonString")

        val regJson = try {
            JSONObject(
                jsonString
            )
        } catch (e: Exception) {
            Log.d(TAG, "registerUser: ERROR_REG_JSON: ${e.message}")
            return null
        }

        return ModelRegister.createFromJSON(
            regJson
        )
    }

}