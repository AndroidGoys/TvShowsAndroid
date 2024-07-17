package good.damn.tvlist.network.api.services

import androidx.annotation.WorkerThread
import good.damn.tvlist.App
import good.damn.tvlist.Unicode
import good.damn.tvlist.enums.SearchResultCategory
import good.damn.tvlist.interfaces.Typeable
import good.damn.tvlist.models.TVSearchResultTitle
import good.damn.tvlist.network.NetworkJSONService
import good.damn.tvlist.network.api.models.TVChannel2
import good.damn.tvlist.network.api.models.TVSearchResult
import good.damn.tvlist.network.api.models.TVSearchResultChannels
import org.json.JSONObject
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class TVChannel2Service
: NetworkJSONService(
    App.CACHE_DIR
) {

    companion object {
        private const val URL_CHANNELS = "${App.URL}/api/channels"
    }

    @WorkerThread
    fun getChannels(
        offset: Int,
        limit: Int,
        name: String,
        fromCache: Boolean = false
    ): ArrayList<TVChannel2>? {
        val json = searchRequest(
            name,
            URL_CHANNELS,
            offset,
            limit,
            fromCache
        )

        if (json == null || json.length() == 0) {
            return null
        }

        val chan = TVSearchResultChannels.createFromJSON(
            json
        )

        if (chan == null) {
            return null
        }

        val len = chan.channels.length()

        val channels = ArrayList<TVChannel2>(
            len
        )

        for (i in 0..<len) {
            val channel = TVChannel2.createFromJSON(
                chan.channels.getJSONObject(i)
            ) ?: continue

            channels.add(
                channel
            )
        }

        return channels
    }

    private fun searchRequest(
        name: String,
        url: String,
        offset: Int,
        limit: Int,
        fromCache: Boolean
    ): JSONObject? {
        val encodedName = URLEncoder.encode(
            name,
            StandardCharsets.UTF_8.name()
        )

        val url = "$url?limit=$limit&offset=$offset&name=$encodedName"

        return if (fromCache)
            getCachedJson(
                url
            ) else getNetworkJSON(
            url
        )
    }

}