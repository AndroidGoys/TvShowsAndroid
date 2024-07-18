package good.damn.tvlist.network.api.services

import android.media.MediaRouter.UserRouteInfo
import android.util.Log
import good.damn.tvlist.App
import good.damn.tvlist.network.NetworkJSONService
import good.damn.tvlist.network.api.models.auth.TokenAuth
import org.json.JSONObject
import java.net.URL

class UserService
: NetworkJSONService(
    App.CACHE_DIR
) {

    companion object {
        private const val TAG = "UserService"
        private const val URL_PROFILE = "${App.URL}/api/users/@me"
    }

    fun getProfile() {
        val response = getNetworkJSON(
            URL_PROFILE
        )

        Log.d(TAG, "getProfile: ${response} ")
    }

}