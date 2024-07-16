package good.damn.tvlist.network.api.models.auth

import android.graphics.ColorSpace.Model
import good.damn.tvlist.extensions.extract
import org.json.JSONObject

data class ModelRegister(
    val accessToken: String,
    val refreshToken: String
) {
    companion object {
        fun createFromJSON(
            json: JSONObject
        ): ModelRegister? {

            val accessToken = json.extract(
                "accessToken"
            ) as? String ?: return null

            val refreshToken = json.extract(
                "refreshToken"
            ) as? String ?: return null

            return ModelRegister(
                accessToken,
                refreshToken
            )

        }
    }
}