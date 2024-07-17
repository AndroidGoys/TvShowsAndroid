package good.damn.tvlist.network.api.services

import android.util.Log
import good.damn.tvlist.App
import good.damn.tvlist.extensions.extract
import good.damn.tvlist.extensions.toJSONObject
import good.damn.tvlist.network.NetworkJSONService
import good.damn.tvlist.network.api.models.auth.TokenAuth
import org.json.JSONObject

class AuthService
: NetworkJSONService(
    App.CACHE_DIR
) {

    companion object {
        private const val TAG = "AuthService"
        private const val URL_REGISTER = "${App.URL}/api/auth/register"
        private const val URL_LOGIN = "${App.URL}/api/auth/login"
        private const val URL_REFRESH = "${App.URL}/api/auth/refresh"
        private const val JSON_TYPE = "application/json"
    }

    fun refreshAccess(
        refreshToken: String
    ): TokenAuth? {
        val json = JSONObject().apply {
            put("refreshToken", refreshToken)
        }

        val response = postJSON(
            URL_REFRESH,
            json,
            JSON_TYPE
        ) ?: return null
        Log.d(TAG, "refreshAccess: RESPONSE: $response")

        val jsonResponse = response.string().toJSONObject()
            ?: return null

        val accessToken = jsonResponse.extract(
            TokenAuth.KEY_ACCESS
        ) as? String ?: return null

        return TokenAuth(
            accessToken,
            refreshToken
        )
    }

    fun login(
        email: String,
        password: String
    ): TokenAuth? {
        val json = JSONObject().apply {
            put("login", email)
            put("password", password)
        }

        val response = postJSON(
            URL_LOGIN,
            json,
            JSON_TYPE
        ) ?: return null
        Log.d(TAG, "login: RESPONSE: $response")

        val str = response.string()
        Log.d(TAG, "login: RESPONSE_STRING: $str")

        val jsonResponse = str.toJSONObject()
            ?: return null

        return TokenAuth.createFromJSON(
            jsonResponse
        )
    }

    fun registerUser(
        email: String,
        password: String,
        username: String
    ): TokenAuth? {

        val json = JSONObject().apply {
            put("username",
                username)

            put("email",
                email)

            put("password",
                password)
        }


        val response = postJSON(
            URL_REGISTER,
            json,
            JSON_TYPE
        ) ?: return null

        Log.d(TAG, "registerUser: RESPONSE: $response")

        val jsonString = response.string()
        Log.d(TAG, "registerUser: RESPONSE_JSON: $jsonString")

        val responseJson = jsonString.toJSONObject()
            ?: return null

        return TokenAuth.createFromJSON(
            responseJson
        )
    }

}