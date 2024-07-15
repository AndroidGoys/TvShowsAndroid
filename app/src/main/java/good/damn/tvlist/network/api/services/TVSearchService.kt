package good.damn.tvlist.network.api.services

import android.util.Log
import good.damn.tvlist.App
import good.damn.tvlist.interfaces.Typeable
import good.damn.tvlist.models.TVSearchResultTitle
import good.damn.tvlist.network.NetworkJSONService
import good.damn.tvlist.network.api.models.TVChannel2
import good.damn.tvlist.network.api.models.TVSearchResult
import good.damn.tvlist.network.api.models.TVSearchResultChannels
import good.damn.tvlist.network.api.models.TVSearchResultShows
import good.damn.tvlist.network.api.models.TVShow
import kotlinx.coroutines.launch
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

    fun getShowsByName(
        name: String,
        completionBackground: (ArrayList<Typeable?>?) -> Unit
    ) {
        searchRequest(
            name,
            URL_SHOWS,
            limit = 25
        ) {
            if (it == null || it.length() == 0) {
                completionBackground(null)
                return@searchRequest
            }

            val showsJson = TVSearchResultShows.createFromJSON(
                it
            )

            if (showsJson == null) {
                completionBackground(null)
                return@searchRequest
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

            completionBackground(
                shows
            )

        }
    }

    fun getChannelsByName(
        name: String,
        completionBackground: (ArrayList<Typeable?>?) -> Unit
    ) {
        searchRequest(
            name,
            URL_CHANNELS,
            limit = 8
        ) {
            if (it == null || it.length() == 0) {
                completionBackground(null)
                return@searchRequest
            }

            val chan = TVSearchResultChannels.createFromJSON(
                it
            )

            if (chan == null) {
                completionBackground(null)
                return@searchRequest
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

            completionBackground(
                channels
            )
        }
    }

    private fun searchRequest(
        name: String,
        url: String,
        limit: Int,
        completionBackground: (JSONObject?) -> Unit
    ) {
        val encodedName = URLEncoder.encode(
            name,
            StandardCharsets.UTF_8.name()
        )
        getJSON(
            "$url?limit=$limit&offset=0&name=$encodedName"
        ) {
            completionBackground(
                it
            )
        }
    }

}