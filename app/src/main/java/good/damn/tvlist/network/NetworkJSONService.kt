package good.damn.tvlist.network

import android.util.Log
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import org.json.JSONObject

open class NetworkJSONService
: NetworkService() {

    companion object {
        private const val TAG = "JSONService"
        private const val ACCEPT = "application/json"
    }

    protected fun postJSON(
        url: String,
        json: JSONObject,
        mediaType: String,
        completionBackground: (ResponseBody) -> Unit
    ) {
        execute(
            makeRequestPOST(
                url,
                ACCEPT,
                json.toString().toRequestBody(
                    mediaType.toMediaType()
                )
            )
        ) {
            val body = it.body
            Log.d(TAG, "getJSON: RESPONSE $it $body")
            if (body == null || it.code != 200) {
                Log.d(TAG, "getJSON: INVALID RESPONSE")
                return@execute
            }

            completionBackground(
                body
            )
        }
    }

    protected fun getJSON(
        url: String,
        completionBackground: (JSONObject?) -> Unit
    ) {
        execute(
            makeRequestGET(
                url,
                ACCEPT
            )
        ) {
            val body = it.body?.string()
            Log.d(TAG, "getJSON: RESPONSE $it $body")
            if (body == null || it.code != 200) {
                Log.d(TAG, "getJSON: INVALID RESPONSE")
                return@execute
            }

            completionBackground(
                try {
                    JSONObject(
                        body
                    )
                } catch (e: Exception) {
                    Log.d(TAG, "getJSON: ERROR: ${e.message}")
                    null
                }
            )
        }
    }

}