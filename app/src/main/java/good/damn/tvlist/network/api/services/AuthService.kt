package good.damn.tvlist.network.api.services

import android.media.session.MediaSession.Token
import android.util.Log
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.extensions.extract
import good.damn.tvlist.extensions.toJSONObject
import good.damn.tvlist.models.Result
import good.damn.tvlist.network.NetworkJSONService
import good.damn.tvlist.network.api.models.auth.TokenAuth
import good.damn.tvlist.network.api.models.error.Error
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
    }

    fun refreshAccess(
        refreshToken: String
    ): Result<TokenAuth> {

        val jsonResult = requestPostJSON(
            URL_REFRESH,
            JSONObject().apply {
                put("refreshToken", refreshToken)
            }
        )

        if (jsonResult.errorStringId != -1) {
            return Result(
                errorStringId = jsonResult.errorStringId
            )
        }

        val accessToken = jsonResult.result?.extract(
            TokenAuth.KEY_ACCESS
        ) as? String ?: return Result(
            errorStringId = R.string.invalid_object
        )

        return Result(
            TokenAuth(
                accessToken,
                refreshToken
            )
        )
    }

    fun login(
        email: String,
        password: String
    ): Result<TokenAuth> {
        val jsonResult = requestPostJSON(
            URL_LOGIN,
            JSONObject().apply {
                put("login", email)
                put("password", password)
            }
        )

        if (jsonResult.errorStringId != -1) {
            return Result(
                errorStringId = jsonResult.errorStringId
            )
        }

        val token = TokenAuth.createFromJSON(
            jsonResult.result
        ) ?: return Result(
            errorStringId = R.string.invalid_object
        )

        return Result(
            token
        )
    }

    fun registerUser(
        email: String,
        password: String,
        username: String
    ): Result<TokenAuth> {

        val result = requestPostJSON(
            URL_REGISTER,
            JSONObject().apply {
                put("username",
                    username)

                put("email",
                    email)

                put("password",
                    password)
            }
        )

        if (result.errorStringId != -1) {
            return Result(
                errorStringId = result.errorStringId
            )
        }

        val json = TokenAuth.createFromJSON(
            result.result
        ) ?: return Result(
            errorStringId = R.string.invalid_object
        )

        return Result(
            json
        )
    }
}