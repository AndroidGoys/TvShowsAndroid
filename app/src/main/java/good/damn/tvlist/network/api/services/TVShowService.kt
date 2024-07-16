package good.damn.tvlist.network.api.services

import androidx.annotation.WorkerThread
import good.damn.tvlist.App
import good.damn.tvlist.network.NetworkJSONService
import good.damn.tvlist.network.api.models.show.TVShowChannelDate
import good.damn.tvlist.network.api.models.show.TVShowImage
import good.damn.tvlist.network.api.models.show.TVShowUserReview
import kotlinx.coroutines.launch
import java.io.File

class TVShowService(
    cacheDirApp: File
): NetworkJSONService(
    cacheDirApp
) {

    companion object {
        private const val TAG = "TVShowChannelsService"
        private const val URL = ""
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