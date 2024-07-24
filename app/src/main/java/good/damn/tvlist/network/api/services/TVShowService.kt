package good.damn.tvlist.network.api.services

import android.util.Log
import androidx.annotation.WorkerThread
import good.damn.tvlist.App
import good.damn.tvlist.extensions.extract
import good.damn.tvlist.extensions.extractArray
import good.damn.tvlist.extensions.extractObject
import good.damn.tvlist.extensions.toGregorianDateString
import good.damn.tvlist.extensions.toTimeString
import good.damn.tvlist.network.NetworkJSONService
import good.damn.tvlist.network.api.models.TVSearchResultShows
import good.damn.tvlist.network.api.models.TVShow
import good.damn.tvlist.network.api.models.show.TVShowChannelDate
import good.damn.tvlist.network.api.models.show.TVShowDetails
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class TVShowService(
): NetworkJSONService(
    App.CACHE_DIR
) {

    companion object {
        private const val TAG = "TVShowChannelsService"
        private const val URL_SHOWS = "${App.URL}/api/shows"
    }

    @WorkerThread
    fun getShowDetails(
        id: Int,
        fromCache: Boolean = false
    ): TVShowDetails? {

        val url = "${URL_SHOWS}/$id"
        val response = if (fromCache)
            getCachedJson(url)
        else getNetworkJSON(url)
        
        if (response == null) {
            return null
        }

        return TVShowDetails.createFromJSON(
            response
        )
    }

    @WorkerThread
    fun getShows(
        name: String,
        offset: Int,
        limit: Int,
        fromCache: Boolean = false
    ): ArrayList<TVShow>? {
        val encodedName = URLEncoder.encode(
            name,
            StandardCharsets.UTF_8.name()
        )

        val url = "${URL_SHOWS}?" +
            "limit=$limit&" +
            "offset=$offset&" +
            "name=$encodedName"

        val json = if (fromCache)
            getCachedJson(url)
        else getNetworkJSON(
            url
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

        val shows = ArrayList<TVShow>(
            len
        )

        for (i in 0..<len) {
            val show = TVShow.createFromJSON(
                showsJson.shows.getJSONObject(i)
            ) ?: continue

            shows.add(
                show
            )
        }

        return shows
    }


    @WorkerThread
    fun getChannelPointers(
        showId: Long,
        fromCache: Boolean = false
    ): ArrayList<TVShowChannelDate>? {
        val url = "$URL_SHOWS/$showId/channels?"

        val json = if (fromCache)
            getCachedJson(url)
        else getNetworkJSON(url)

        Log.d(TAG, "getChannelPointers: $json")
        
        if (json == null) {
            return null
        }

        val channels = json.extractArray(
            "channels"
        )

        val len = channels?.length() ?: 0

        if (len == 0) {
            return null
        }

        val channelDates = ArrayList<TVShowChannelDate>(
            len
        )

        for (i in 0..<len) {
            val channel = channels
                ?.getJSONObject(i)
                ?: continue

            val imageUrl = channel.extract(
                "imageUrl"
            ) as? String

            val name = channel.extract(
                "name"
            ) as? String

            val channelId = channel.extract(
                "id"
            ) as? Long ?: -1

            val releases = channel.extractObject(
                "releases"
            )?.extractArray(
                "releases"
            )

            val len = releases?.length() ?: 0

            if (len == 0) {
                continue
            }

            val timeStart = releases!!
                .getJSONObject(i)
                .extract("timeStart")
            as? Int ?: continue

            channelDates.add(
                TVShowChannelDate(
                    channelId,
                    timeStart.toTimeString(),
                    timeStart.toGregorianDateString(),
                    imageUrl,
                    name
                )
            )

        }

        return channelDates
    }


}