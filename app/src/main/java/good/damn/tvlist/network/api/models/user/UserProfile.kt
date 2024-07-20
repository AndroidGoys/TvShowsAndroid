package good.damn.tvlist.network.api.models.user

import good.damn.tvlist.extensions.extract
import org.json.JSONObject

data class UserProfile(
    val id: Int,
    val username: String,
    val email: String?,
    val avatarUrl: String?,
    val registrationDateSeconds: Int
) {
    companion object {
        fun createFromJSON(
            json: JSONObject
        ): UserProfile? {
            val username = json.extract(
                "nickname"
            ) as? String ?: return null

            val id = json.extract(
                "id"
            ) as? Int ?: return null

            val email = json.extract(
                "email"
            ) as? String

            val registrationDateSeconds = json.extract(
                "registrationDateSeconds"
            ) as? Int ?: 0

            val avatarUrl = json.extract(
                "avatarUrl"
            ) as? String ?: "https://w7.pngwing.com/pngs/205/731/png-transparent-default-avatar-thumbnail.png"

            return UserProfile(
                id,
                username,
                email,
                avatarUrl,
                registrationDateSeconds
            )
        }
    }
}