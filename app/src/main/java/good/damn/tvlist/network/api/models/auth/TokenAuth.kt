package good.damn.tvlist.network.api.models.auth

import good.damn.tvlist.extensions.extract
import org.json.JSONObject

data class TokenAuth(
    val accessToken: String,
    val refreshToken: String
) {
    companion object {
        const val KEY_ACCESS = "accessToken"
        fun createFromJSON(
            json: JSONObject
        ): TokenAuth? {

            val accessToken = json.extract(
                KEY_ACCESS
            ) as? String ?: return null

            val refreshToken = json.extract(
                "refreshToken"
            ) as? String ?: return null

            return TokenAuth(
                accessToken,
                refreshToken
            )

        }
    }
}