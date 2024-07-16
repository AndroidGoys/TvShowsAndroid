package good.damn.tvlist.network

import android.util.Log
import good.damn.tvlist.cache.CacheJSON
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import java.io.File

open class NetworkJSONService(
    private val mCacheDirApp: File
): NetworkService() {

    companion object {
        private const val TAG = "JSONService"
        private const val ACCEPT = "application/json"
    }

    protected fun postJSON(
        url: String,
        json: JSONObject,
        mediaType: String
    ): ResponseBody? {
        val response = execute(
            makeRequestPOST(
                url,
                ACCEPT,
                json.toString().toRequestBody(
                    mediaType.toMediaType()
                )
            )
        )

        if (response == null) {
            return null
        }

        val body = response.body
        Log.d(TAG, "getJSON: RESPONSE $response $body")
        if (body == null || response.code != 200) {
            Log.d(TAG, "getJSON: INVALID RESPONSE")
            return null
        }

        return body
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
        url: String
    ): JSONObject? {
        val response = execute(
            makeRequestGET(
                url,
                ACCEPT
            )
        )

        if (response == null) {
            return null
        }

        val body = response.body
        Log.d(TAG, "getJSON: RESPONSE $response $body")
        if (body == null || response.code != 200) {
            Log.d(TAG, "getJSON: INVALID RESPONSE")
            return null
        }

        val json = body.string()

        CacheJSON.cache(
            json,
            url.hashCode().toString(),
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