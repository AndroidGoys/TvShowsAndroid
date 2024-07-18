package good.damn.tvlist.network.api.services.lime

import android.util.Log
import androidx.annotation.WorkerThread
import good.damn.tvlist.network.NetworkJSONService
import good.damn.tvlist.network.api.models.TVChannel
import org.json.JSONObject
import java.io.File

class TVChannelsService(
    cacheDirApp: File
): NetworkJSONService(
    cacheDirApp
) {

    companion object {
        private const val TAG = "TVChannelsService"
        private const val URL = "https://limehd.tv/api/v4/playlist"
    }

    @WorkerThread
    fun getChannels(
        from: Int,
        limit: Int,
        fromCache: Boolean = false
    ): ArrayList<TVChannel?>? {
        val url = "$URL?page=$from&limit=$limit&epg=1&epgcnt=15"

        if (!fromCache) {
            val json = getNetworkJSON(url)
            return extractJsonChannels(
                limit,
                json
            )
        }

        val cachedJson = getCachedJson(url)
        return extractJsonChannels(
            limit,
            cachedJson
        )
    }

    private fun extractJsonChannels(
        limit: Int,
        json: JSONObject?
    ): ArrayList<TVChannel?>? {
        if (json == null) {
            Log.d(TAG, "extractJsonChannels: INVALID JSON")
            return null
        }

        val jsonArray = processJsonChannels(
            json
        ) ?: return null

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

        return tvChannels
    }

    private fun processJsonChannels(
        obj: JSONObject
    ) = try {
        obj.getJSONArray(
            "channels"
        )
    } catch (e: Exception) {
        Log.d(TAG, "processJsonChannels: NO_CHANNELS: ${e.message}")
        null
    }

}