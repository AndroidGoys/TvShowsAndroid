package good.damn.tvlist.network

import good.damn.tvlist.App
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response

open class NetworkService {

    private val mRequestBuilder = Request.Builder()
        .header("User-Agent", App.USER_AGENT)

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
        App.IO.launch {
            val response = mClient.newCall(
                request
            ).execute()

            completionBackground(
                response
            )
        }
    }

}