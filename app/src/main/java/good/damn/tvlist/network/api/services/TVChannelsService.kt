package good.damn.tvlist.network.api.services

import android.content.Context
import android.util.Log
import android.webkit.WebView
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.extensions.readBytes
import good.damn.tvlist.network.NetworkJSONService
import good.damn.tvlist.network.api.models.TVChannel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.nio.charset.Charset
import kotlin.math.log

class TVChannelsService(
    cacheDirApp: File
): NetworkJSONService(
    cacheDirApp
) {

    companion object {
        private const val TAG = "TVChannelsService"
        private const val URL = "https://limehd.tv/api/v4/playlist"
    }

    fun getChannels(
        from: Int,
        limit: Int,
        completionBackground: (ArrayList<TVChannel?>) -> Unit
    ) {
        getJSON(
            "$URL?page=$from&limit=$limit&epg=1&epgcnt=15"
        ) {
            if (it == null) {
                Log.d(TAG, "getChannels: INVALID JSON")
                return@getJSON
            }
            
            val jsonArray = processJsonChannels(
                it
            ) ?: return@getJSON

            val tvChannels = ArrayList<TVChannel?>(
                limit
            )

            for (i in 0..<jsonArray.length()) {
                tvChannels.add(
                    TVChannel.createFromJSON(
                        jsonArray.getJSONObject(i)
                    )
                )
            }

            completionBackground(
                tvChannels
            )
        }
    }

    private fun processJsonChannels(
        obj: JSONObject
    ): JSONArray? {
        val channels = try {
            obj.getJSONArray(
                "channels"
            )
        } catch (e: Exception) {
            Log.d(TAG, "processJsonChannels: NO_CHANNELS: ${e.message}")
            return null
        }

        Log.d(TAG, "processJsonChannels: CHANNELS_COUNT: ${channels.length()}")

        return channels
    }

}