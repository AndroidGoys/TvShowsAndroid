package good.damn.tvlist.network.api.services

import androidx.annotation.WorkerThread
import good.damn.tvlist.App
import good.damn.tvlist.extensions.extract
import good.damn.tvlist.extensions.extractArray
import good.damn.tvlist.network.NetworkJSONService
import good.damn.tvlist.network.api.models.TVChannelRelease

class TVChannelReleasesService
: NetworkJSONService(
    App.CACHE_DIR
) {

    companion object {
        private const val URL = "${App.URL}/api/channels"
    }

    @WorkerThread
    fun getReleases(
        channelId: Int,
        timeStartSeconds: Int,
        limit: Int,
        fromCache: Boolean = false
    ): ArrayList<TVChannelRelease>? {
        val cacheUrl = "$URL/$channelId/releases?limit=$limit"
        val url = "$cacheUrl&time-start=$timeStartSeconds"
        val json = if (fromCache)
            getCachedJson(cacheUrl)
        else getNetworkJSON(
            url,
            cacheUrl
        )

        if (json == null) {
            return null
        }

        val jsonReleases = json.extractArray(
            "releases"
        )

        val len = jsonReleases?.length() ?: 0

        if (len == 0) {
            return null
        }

        val releases = ArrayList<TVChannelRelease>(
            jsonReleases!!.length()
        )

        for (i in 0..<len) {
            val release = TVChannelRelease.createFromJSON(
                jsonReleases.getJSONObject(i)
            ) ?: continue

            releases.add(
                release
            )
        }

        return releases

    }

}