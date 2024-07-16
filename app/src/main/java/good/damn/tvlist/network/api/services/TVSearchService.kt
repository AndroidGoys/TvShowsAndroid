package good.damn.tvlist.network.api.services

import androidx.annotation.WorkerThread
import good.damn.tvlist.App
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
    ): ArrayList<Typeable?>? {
        val json = searchRequest(
            name,
            URL_SHOWS,
            limit = 25
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

            shows.add(
                TVSearchResult(
                    show.name,
                    show.previewUrl
                )
            )
        }

        return shows
    }

    @WorkerThread
    fun getChannelsByName(
        name: String
    ): ArrayList<Typeable?>? {
        val json = searchRequest(
            name,
            URL_CHANNELS,
            limit = 8
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

            channels.add(
                TVSearchResult(
                    channel.name,
                    channel.imageUrl
                )
            )
        }

        return channels
    }

    private fun searchRequest(
        name: String,
        url: String,
        limit: Int
    ): JSONObject? {
        val encodedName = URLEncoder.encode(
            name,
            StandardCharsets.UTF_8.name()
        )

        return getNetworkJSON(
            "$url?limit=$limit&offset=0&name=$encodedName"
        )
    }

}