package good.damn.tvlist.network

import android.util.Log
import good.damn.tvlist.App
import kotlinx.coroutines.launch
import okhttp3.CacheControl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response

open class NetworkService {

    companion object {
        private const val TAG = "NetworkService"
    }

    private val mRequestBuilder = Request.Builder()
        .addHeader("User-Agent", App.USER_AGENT)
        .cacheControl(CacheControl.FORCE_NETWORK)

    private val mClient = OkHttpClient()

    protected fun makeRequestPOST(
        url: String,
        accept: String,
        body: RequestBody
    ) = mRequestBuilder
        .url(url)
        .addHeader(
            "Accept", accept
        ).post(body)
        .build()

    protected fun makeRequestGET(
        url: String,
        accept: String
    ) = mRequestBuilder
        .url(url)
        .addHeader(
            "Accept",
            accept
        ).get()
        .build()

    protected fun execute(
        request: Request,
        completionBackground: (Response) -> Unit
    ) {
        if (!App.NETWORK_AVAILABLE) {
            return
        }

        App.IO.launch {
            val response = try {
                mClient.newCall(
                    request
                ).execute()
            } catch (e: Exception) {
                Log.d(TAG, "execute: ERROR CONNECTION: ${e.message}")
                return@launch
            }

            completionBackground(
                response
            )
        }
    }

}