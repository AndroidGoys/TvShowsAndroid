package good.damn.tvlist.network.api.services

import androidx.annotation.WorkerThread
import good.damn.tvlist.App
import good.damn.tvlist.Unicode
import good.damn.tvlist.enums.SearchResultCategory
import good.damn.tvlist.interfaces.Typeable
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
    fun getReviews(
        showId: Long
    ): ArrayList<TVShowUserReview> {
        return arrayListOf(
            TVShowUserReview(
                "Киберниндзя2077",
                5,
                dateString = "12.07.2024",
                "Крайне скептически отношусь к российским комедийным (да и остальным тоже) сериалам. Все эти ситкомы с закадровым смехом, ремейки популярных зарубежных многосезонников, попытки создать что-то свое... Скучно, пошло, не смешно.  Случайно наткнулся на отрывок из первой серии, начал смотреть. Создателям удалось смешать самые разные 'ингредиенты' в нужных пропорциях. Получилось очень занятное блюдо",
                usernameImageUrl = "https://yt3.googleusercontent.com/skAODIZ07KvllI7tXL6ERzwZn1ECRCdIPehY4dV5AvaD9xxu6L2Q-rm6his2ImwQgSGISmJtgg=s900-c-k-c0x00ffffff-no-rj"
            ),
            TVShowUserReview(
                "Gigachad777",
                4,
                dateString = "13.07.2024",
                "Довольно таки прикольный сериал, выделяется из серии однообразных комедий на СТС, порадовало отсутствие закадрового смеха и реалистичность сюжета.  Когда я увидела рекламу, то даже не думала смотреть этот сериал, но случайно на него наткнулась и была приятно удивлена - все достаточно прилично и красиво, правдиво и весело. Особенно хочу выделить музыкальное сопровождение этого сериала, уж очень запало оно в мою душу - в этом плане все также свежо и ново.  А теперь я хочу немного рассказать о самом сериале.  Вся эта картина нам рассказывает о жизни простого парня - Максима Лаврова, который пришел работать поваром в элитный ресторан, там конечно же его ждали как неприятности, так и приятные неожиданности, любовь и разочарование, обиды и радости, но все это вылилось в весьма неплохой сюжет.",
                usernameImageUrl = "https://upload.wikimedia.org/wiktionary/en/thumb/6/6c/Ernest_Khalimov.png/220px-Ernest_Khalimov.png"
            ),
            TVShowUserReview(
                "Киберниндзя2077",
                5,
                dateString = "12.07.2024",
                "Крайне скептически отношусь к российским комедийным (да и остальным тоже) сериалам. Все эти ситкомы с закадровым смехом, ремейки популярных зарубежных многосезонников, попытки создать что-то свое... Скучно, пошло, не смешно.  Случайно наткнулся на отрывок из первой серии, начал смотреть. Создателям удалось смешать самые разные 'ингредиенты' в нужных пропорциях. Получилось очень занятное блюдо",
                usernameImageUrl = "https://yt3.googleusercontent.com/skAODIZ07KvllI7tXL6ERzwZn1ECRCdIPehY4dV5AvaD9xxu6L2Q-rm6his2ImwQgSGISmJtgg=s900-c-k-c0x00ffffff-no-rj"
            ),
            TVShowUserReview(
                "Gigachad777",
                4,
                dateString = "13.07.2024",
                "Довольно таки прикольный сериал, выделяется из серии однообразных комедий на СТС, порадовало отсутствие закадрового смеха и реалистичность сюжета.  Когда я увидела рекламу, то даже не думала смотреть этот сериал, но случайно на него наткнулась и была приятно удивлена - все достаточно прилично и красиво, правдиво и весело. Особенно хочу выделить музыкальное сопровождение этого сериала, уж очень запало оно в мою душу - в этом плане все также свежо и ново.  А теперь я хочу немного рассказать о самом сериале.  Вся эта картина нам рассказывает о жизни простого парня - Максима Лаврова, который пришел работать поваром в элитный ресторан, там конечно же его ждали как неприятности, так и приятные неожиданности, любовь и разочарование, обиды и радости, но все это вылилось в весьма неплохой сюжет.",
                usernameImageUrl = "https://upload.wikimedia.org/wiktionary/en/thumb/6/6c/Ernest_Khalimov.png/220px-Ernest_Khalimov.png"
            ),
            TVShowUserReview(
                "Киберниндзя2077",
                5,
                dateString = "12.07.2024",
                "Крайне скептически отношусь к российским комедийным (да и остальным тоже) сериалам. Все эти ситкомы с закадровым смехом, ремейки популярных зарубежных многосезонников, попытки создать что-то свое... Скучно, пошло, не смешно.  Случайно наткнулся на отрывок из первой серии, начал смотреть. Создателям удалось смешать самые разные 'ингредиенты' в нужных пропорциях. Получилось очень занятное блюдо",
                usernameImageUrl = "https://yt3.googleusercontent.com/skAODIZ07KvllI7tXL6ERzwZn1ECRCdIPehY4dV5AvaD9xxu6L2Q-rm6his2ImwQgSGISmJtgg=s900-c-k-c0x00ffffff-no-rj"
            ),
            TVShowUserReview(
                "Gigachad777",
                4,
                dateString = "13.07.2024",
                "Довольно таки прикольный сериал, выделяется из серии однообразных комедий на СТС, порадовало отсутствие закадрового смеха и реалистичность сюжета.  Когда я увидела рекламу, то даже не думала смотреть этот сериал, но случайно на него наткнулась и была приятно удивлена - все достаточно прилично и красиво, правдиво и весело. Особенно хочу выделить музыкальное сопровождение этого сериала, уж очень запало оно в мою душу - в этом плане все также свежо и ново.  А теперь я хочу немного рассказать о самом сериале.  Вся эта картина нам рассказывает о жизни простого парня - Максима Лаврова, который пришел работать поваром в элитный ресторан, там конечно же его ждали как неприятности, так и приятные неожиданности, любовь и разочарование, обиды и радости, но все это вылилось в весьма неплохой сюжет.",
                usernameImageUrl = "https://upload.wikimedia.org/wiktionary/en/thumb/6/6c/Ernest_Khalimov.png/220px-Ernest_Khalimov.png"
            )
        )

    }

    @WorkerThread
    fun getImages(): Array<TVShowImage> {
        val data = arrayOf(
            TVShowImage(
                "https://i.ytimg.com/vi/3AdWkAR6VwE/hq720.jpg"
            ),
            TVShowImage(
                "https://i.ytimg.com/vi/yb1XRLlcDrY/hq720.jpg"
            ),
            TVShowImage(
                "https://i.ytimg.com/vi/TOfJEgcEkXo/hq720.jpg"
            ),
            TVShowImage(
                "https://i.ytimg.com/vi/rZKSI9BOZZY/hq720.jpg"
            )
        )

        return data
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