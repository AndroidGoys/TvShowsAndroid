package good.damn.tvlist.network.api.services

import android.media.MediaRouter.UserRouteInfo
import android.util.Log
import androidx.annotation.WorkerThread
import good.damn.tvlist.App
import good.damn.tvlist.network.NetworkJSONService
import good.damn.tvlist.network.api.models.auth.TokenAuth
import good.damn.tvlist.network.api.models.user.UserProfile
import org.json.JSONObject
import java.net.URL

class UserService
: NetworkJSONService(
    App.CACHE_DIR
) {

    companion object {
        private const val TAG = "UserService"
        private const val URL_PROFILE = "${App.URL}/api/users/@me"
        private const val URL_PROFILE_ID = "${App.URL}/api/users"
        const val DIR_AVATAR = "bitmapAvatars"
    }

    @WorkerThread
    fun getProfile(
        userId: Long,
        fromCache: Boolean = false
    ): UserProfile? {
        val url = "$URL_PROFILE_ID/$userId"

        val result = if (fromCache)
            getCachedJson(url)
        else getNetworkJSON(url)

        if (result == null) {
            return null
        }

        return UserProfile.createFromJSON(
            result
        )
    }

    @WorkerThread
    fun getProfile(
        fromCache: Boolean = false
    ): UserProfile? {
        val response = if (fromCache)
            getCachedJson(URL_PROFILE)
        else getNetworkJSON(
            URL_PROFILE
        )

        if (response == null) {
            return null
        }

        return UserProfile.createFromJSON(
            response
        )
    }

}