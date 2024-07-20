package good.damn.tvlist.network.api.services

import android.graphics.Bitmap
import android.media.MediaRouter.UserRouteInfo
import android.util.Log
import androidx.annotation.WorkerThread
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.network.NetworkJSONService
import good.damn.tvlist.network.api.models.auth.TokenAuth
import good.damn.tvlist.network.api.models.user.UserProfile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.File
import java.net.URL
import java.nio.charset.StandardCharsets
import good.damn.tvlist.models.Result

class UserService
: NetworkJSONService(
    App.CACHE_DIR
) {

    companion object {
        private const val TAG = "UserService"
        private const val URL_PROFILE = "${App.URL}/api/users/@me"
        private const val URL_AVATAR = "$URL_PROFILE/avatar"
        private const val URL_PROFILE_ID = "${App.URL}/api/users"
        private val CONTENT_TYPE_IMG = "image/png".toMediaType()
        const val DIR_AVATAR = "bitmapAvatars"
    }

    @WorkerThread
    fun uploadAvatar(
        bytes: ByteArray
    ): Result<Void> {
        val response = execute(
            makeRequestPOST2(
                URL_AVATAR,
                "application/json",
                bytes.toRequestBody(
                    CONTENT_TYPE_IMG
                )
            ).build()
        ) ?: return Result(
            errorStringId = R.string.invalid_response
        )

        Log.d(TAG, "uploadAvatar: ${response} ${response.body?.string()}")
        return Result()
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