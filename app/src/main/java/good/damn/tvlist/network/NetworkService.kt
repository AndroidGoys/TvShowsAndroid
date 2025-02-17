package good.damn.tvlist.network

import android.util.Log
import good.damn.tvlist.App
import good.damn.tvlist.extensions.toAuthToken
import good.damn.tvlist.network.api.services.AuthService
import kotlinx.coroutines.launch
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.internal.EMPTY_REQUEST
import java.io.File

open class NetworkService {

    companion object {
        private const val TAG = "NetworkService"
        private const val KEY_AUTH = "Authorization"
    }

    private val mRequestBuilder = Request.Builder()
        .addHeader("User-Agent", App.USER_AGENT)
        .addHeader(KEY_AUTH, App
            .TOKEN_AUTH
            ?.accessToken
            ?.toAuthToken() ?: ""
        )

    private val mClient = OkHttpClient()

    fun updateAccessToken(
        accessToken: String
    ) = mRequestBuilder
        .removeHeader(KEY_AUTH)
        .addHeader(KEY_AUTH, accessToken.toAuthToken())

    protected fun makeRequestPOST2(
        url: String,
        accept: String,
        body: RequestBody
    ) = mRequestBuilder
        .url(url)
        .addHeader(
            "Accept", accept
        ).post(body)

    protected fun makeRequestDelete(
        url: String,
        body: RequestBody? = EMPTY_REQUEST
    ) = mRequestBuilder
        .url(url)
        .delete(body)
        .build()

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

        return try {
            mClient.newCall(
                request
            ).execute()
        } catch (e: Exception) {
            Log.d(TAG, "execute: ERROR CONNECTION: ${e.localizedMessage}")
            null
        }
    }

}