package good.damn.tvlist.network

import android.util.Log
import good.damn.tvlist.App
import kotlinx.coroutines.launch
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import java.io.File

open class NetworkService {

    companion object {
        private const val TAG = "NetworkService"
    }

    private val mRequestBuilder = Request.Builder()
        .addHeader("User-Agent", App.USER_AGENT)
        .addHeader("Authorization", "Bearer ${App.TOKEN_AUTH?.accessToken}")

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
        request: Request
    ): Response? {
        if (!App.NETWORK_AVAILABLE) {
            return null
        }

        Log.d(TAG, "execute: HEADERS: ${request.headers}")
        
        return try {
            mClient.newCall(
                request
            ).execute()
        } catch (e: Exception) {
            Log.d(TAG, "execute: ERROR CONNECTION: ${e.message}")
            null
        }
    }

}