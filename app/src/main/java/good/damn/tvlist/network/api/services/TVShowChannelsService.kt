package good.damn.tvlist.network.api.services

import good.damn.tvlist.App
import good.damn.tvlist.network.NetworkJSONService
import good.damn.tvlist.network.api.models.show.TVShowChannelDate
import kotlinx.coroutines.launch
import java.io.File

class TVShowChannelsService(
    cacheDirApp: File
): NetworkJSONService(
    cacheDirApp
) {

    companion object {
        private const val TAG = "TVShowChannelsService"
        private const val URL = ""
    }

    fun getChannelPointers(
        completion: (Array<TVShowChannelDate>) -> Unit
    ) {

        App.IO.launch {
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
            App.ui {
                completion(data)
            }
        }

    }

}