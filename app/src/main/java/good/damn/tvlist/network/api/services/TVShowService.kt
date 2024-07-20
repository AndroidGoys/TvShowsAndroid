package good.damn.tvlist.network.api.services

import android.util.Log
import androidx.annotation.WorkerThread
import good.damn.tvlist.App
import good.damn.tvlist.Unicode
import good.damn.tvlist.enums.SearchResultCategory
import good.damn.tvlist.extensions.extractArray
import good.damn.tvlist.interfaces.Typeable
import good.damn.tvlist.models.Result
import good.damn.tvlist.models.TVSearchResultTitle
import good.damn.tvlist.network.NetworkJSONService
import good.damn.tvlist.network.api.models.TVSearchResult
import good.damn.tvlist.network.api.models.TVSearchResultShows
import good.damn.tvlist.network.api.models.TVShow
import good.damn.tvlist.network.api.models.show.TVShowChannelDate
import good.damn.tvlist.network.api.models.show.TVShowDetails
import good.damn.tvlist.network.api.models.show.TVShowImage
import good.damn.tvlist.network.api.models.show.TVShowUserReview
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.File
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
    fun postReview(
        showId: Long,
        grade: Byte,
        comment: String = ""
    ): Result<TVShowUserReview> {
        val url = "$URL_SHOWS/$showId/reviews"

        val response = requestPostJSON(
            url,
            JSONObject().apply {
                put("assessment", grade.toInt())
                put("text", comment)
            }
        )

        Log.d(TAG, "postReview: ${response.result}")

        if (response.errorStringId != -1) {
            return Result(
                errorStringId = response.errorStringId
            )
        }

        return Result()
    }


    @WorkerThread
    fun getReviews(
        showId: Long,
        fromCache: Boolean = false
    ): ArrayList<TVShowUserReview>? {

        val url = "$URL_SHOWS/$showId/reviews"

        val response = if (fromCache)
            getCachedJson(url)
        else getNetworkJSON(url)

        Log.d(TAG, "getReviews: RESPONSE: $response")

        if (response == null) {
            return null
        }

        val comments = response.extractArray(
            "comments"
        ) ?: return null

        val reviews = ArrayList<TVShowUserReview>(
            comments.length()
        )

        for (i in 0..<comments.length()) {
            val comment = TVShowUserReview.createFromJSON(
                comments.getJSONObject(i)
            ) ?: return null

            reviews.add(
                comment
            )
        }

        return reviews
    }

    @WorkerThread
    fun getChannelPointers(): Array<TVShowChannelDate> {
        val data = arrayOf(
            TVShowChannelDate(
                "13:10",
                "26.07",
                imageUrl = "https://assets-iptv2022.cdnvideo.ru/static/channel/126/logo_256_1655445109.png"
            ),
            TVShowChannelDate(
                "18:10",
                "28.07",
                imageUrl = "https://assets-iptv2022.cdnvideo.ru/static/channel/67/logo_256_1692687479.png"
            ),
            TVShowChannelDate(
                "22:10",
                "29.07",
                imageUrl = "https://assets-iptv2022.cdnvideo.ru/static/channel/109/logo_256_1655448739.png"
            ),
            TVShowChannelDate(
                "23:40",
                "01.08",
                imageUrl = "https://assets-iptv2022.cdnvideo.ru/static/channel/105/logo_256_1655386697.png"
            )
        )
        return data
    }


}