package good.damn.tvlist.network

import android.util.Log
import good.damn.tvlist.R
import good.damn.tvlist.cache.CacheJSON
import good.damn.tvlist.extensions.toJSONObject
import good.damn.tvlist.models.Result
import good.damn.tvlist.network.api.models.error.Error
import good.damn.tvlist.network.api.services.AuthService
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import org.json.JSONObject
import java.io.File

open class NetworkJSONService(
    private val mCacheDirApp: File
): NetworkService() {

    companion object {
        private const val TAG = "JSONService"
        private const val ACCEPT = "application/json"
        private val MEDIA_TYPE_JSON = "application/json".toMediaType()
    }


    protected fun requestPostJSON(
        url: String,
        json: JSONObject
    ): Result<JSONObject> {

        val resp = execute(
            makeRequestPOST(
                url,
                ACCEPT,
                json.toString().toRequestBody(
                    MEDIA_TYPE_JSON
                )
            )
        ) ?: return Result(
            errorStringId = R.string.invalid_response
        )

        val body = resp.body?.string()
        Log.d(TAG, "requestPostJSON: RESPONSE: $resp $body")

        if (body == null) {
            return Result(
                errorStringId = R.string.invalid_body
            )
        }

        val jsonResponse = body.toJSONObject()
            ?: return Result(
                errorStringId = R.string.invalid_object
            )

        if (resp.code == 200) {
            return Result(
                jsonResponse
            )
        }

        Error.createFromJSON(
            jsonResponse
        )?.let {
            return Result(
                errorStringId = it.msgId
            )
        }

        return Result(
            errorStringId = R.string.some_error_happens
        )

    }

    protected fun getCachedJson(
        url: String
    ): JSONObject? {
        val cachedJson = CacheJSON.load(
            url.hashCode().toString(),
            mCacheDirApp
        )

        Log.d(TAG, "getCachedJson: CACHED_JSON ${cachedJson?.length()}")
        return cachedJson
    }

    protected fun getNetworkJSON(
        url: String,
        cacheUrl: String = url
    ): JSONObject? {
        val response = execute(
            makeRequestGET(
                url,
                ACCEPT
            )
        )

        val json = response?.body?.string()
        Log.d(TAG, "getJSON: RESPONSE $response $json")

        if (response == null) {
            return null
        }

        if (json == null || response.code != 200) {
            Log.d(TAG, "getJSON: INVALID RESPONSE")
            return null
        }

        CacheJSON.cache(
            json,
            cacheUrl.hashCode().toString(),
            mCacheDirApp
        )

        return try {
            JSONObject(
                json
            )
        } catch (e: Exception) {
            Log.d(TAG, "getJSON: ERROR: ${e.message}")
            null
        }
    }

}