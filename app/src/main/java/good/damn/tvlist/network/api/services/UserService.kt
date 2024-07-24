package good.damn.tvlist.network.api.services

import android.util.Log
import androidx.annotation.WorkerThread
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.network.NetworkJSONService
import good.damn.tvlist.network.api.models.user.UserProfile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import good.damn.tvlist.models.Result
import org.json.JSONObject

class UserService
: NetworkJSONService(
    App.CACHE_DIR
) {

    companion object {
        private const val TAG = "UserService"
        private const val URL_PROFILE = "${App.URL}/api/users/@me"
        private const val URL_PROFILE_ID = "${App.URL}/api/users"
        private const val URL_FAVOURITE_SHOWS = "${App.URL}/api/shows/favorites"

        private val CONTENT_TYPE_IMG = "image/png".toMediaType()

        const val URL_USER_AVATAR = "$URL_PROFILE/avatar"
        const val DIR_AVATAR = "bitmapAvatars"
    }

    @WorkerThread
    fun getFavouriteShows(): ArrayList<Long>? {
        val resp = getNetworkJSON(
            URL_FAVOURITE_SHOWS
        ) ?: return null

        Log.d(TAG, "getFavouriteShows: $resp")

        return null
    }

    @WorkerThread
    fun deleteFavouriteShow(
        id: Long
    ) {
        val resp = execute(
            makeRequestDelete(
                URL_FAVOURITE_SHOWS,
            )
        )
    }

    @WorkerThread
    fun postFavouriteShow(
        id: Long
    ) {
        val resp = requestPostJSON(
            URL_FAVOURITE_SHOWS,
            JSONObject().apply {
                put("id", id)
            }
        )

        Log.d(TAG, "postFavouriteShow: $resp ${resp.result}")

    }

    @WorkerThread
    fun uploadAvatar(
        bytes: ByteArray
    ): Result<Void> {
        val response = execute(
            makeRequestPOST2(
                URL_USER_AVATAR,
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