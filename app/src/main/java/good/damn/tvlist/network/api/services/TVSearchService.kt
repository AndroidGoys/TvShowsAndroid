package good.damn.tvlist.network.api.services

import androidx.annotation.WorkerThread
import good.damn.tvlist.App
import good.damn.tvlist.Unicode
import good.damn.tvlist.interfaces.Typeable
import good.damn.tvlist.models.TVSearchResultTitle
import good.damn.tvlist.network.NetworkJSONService
import good.damn.tvlist.network.api.models.TVChannel2
import good.damn.tvlist.network.api.models.TVSearchResult
import good.damn.tvlist.network.api.models.TVSearchResultChannels
import good.damn.tvlist.network.api.models.TVSearchResultShows
import good.damn.tvlist.network.api.models.TVShow
import org.json.JSONObject
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class TVSearchService
: NetworkJSONService(
    App.CACHE_DIR
) {
    companion object {
        private const val TAG = "TVSearchService"
        private const val URL_CHANNELS = "${App.URL}/api/channels"
        private const val URL_SHOWS = "${App.URL}/api/shows"
    }

    @WorkerThread
    fun getShowsByName(
        name: String,
        fromCache: Boolean = false
    ): ArrayList<Typeable?>? {
        val json = searchRequest(
            name,
            URL_SHOWS,
            limit = 25,
            fromCache
        )

        if (json == null || json.length() == 0) {
            return null
        }

        val showsJson = TVSearchResultShows.createFromJSON(
            json
        )

        if (showsJson == null) {
            return null
        }

        val len = showsJson.shows.length()

        val shows = ArrayList<Typeable?>(
            len + 1
        )

        shows.add(
            TVSearchResultTitle(
                "Шоу"
            )
        )

        for (i in 0..<len) {
            val show = TVShow.createFromJSON(
                showsJson.shows.getJSONObject(i)
            ) ?: continue

            val showName = if (show.name.length >= 26)
                show.name.substring(0,26) + Unicode.DOTS
            else show.name

            shows.add(
                TVSearchResult(
                    showName,
                    show.previewUrl
                )
            )
        }

        return shows
    }

    @WorkerThread
    fun getChannelsByName(
        name: String,
        fromCache: Boolean = false
    ): ArrayList<Typeable?>? {
        val json = searchRequest(
            name,
            URL_CHANNELS,
            limit = 8,
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

        val channels = ArrayList<Typeable?>(
            len + 1
        )

        channels.add(
            TVSearchResultTitle(
                "Каналы"
            )
        )

        for (i in 0..<len) {
            val channel = TVChannel2.createFromJSON(
                chan.channels.getJSONObject(i)
            ) ?: continue

            val channelName = if (channel.name.length >= 26)
                channel.name.substring(0,26) + Unicode.DOTS
            else channel.name

            channels.add(
                TVSearchResult(
                    channelName,
                    channel.imageUrl
                )
            )
        }

        return channels
    }

    private fun searchRequest(
        name: String,
        url: String,
        limit: Int,
        fromCache: Boolean
    ): JSONObject? {
        val encodedName = URLEncoder.encode(
            name,
            StandardCharsets.UTF_8.name()
        )

        val url = "$url?limit=$limit&offset=0&name=$encodedName"

        return if (fromCache)
            getCachedJson(
                url
        ) else getNetworkJSON(
            url
        )
    }

}