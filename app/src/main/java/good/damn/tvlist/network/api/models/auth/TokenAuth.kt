package good.damn.tvlist.network.api.models.auth

import good.damn.tvlist.extensions.extract
import org.json.JSONObject

data class TokenAuth(
    val accessToken: String,
    val refreshToken: String
) {
    companion object {
        const val KEY_ACCESS = "accessToken"
        const val KEY_REFRESH = "refreshToken"
        fun createFromJSON(
            json: JSONObject?
        ): TokenAuth? {
            json ?: return null

            val accessToken = json.extract(
                KEY_ACCESS
            ) as? String ?: return null

            val refreshToken = json.extract(
                KEY_REFRESH
            ) as? String ?: return null

            return TokenAuth(
                accessToken,
                refreshToken
            )

        }
    }
}