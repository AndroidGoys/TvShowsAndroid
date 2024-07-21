package good.damn.tvlist.network.api.models

import good.damn.tvlist.Unicode
import good.damn.tvlist.extensions.extract
import good.damn.tvlist.extensions.toTimeString
import good.damn.tvlist.network.api.models.enums.CensorAge
import org.json.JSONObject

data class TVChannelRelease(
    val showId: Long,
    val name: String,
    val censorAge: CensorAge,
    val rating: Float,
    val previewUrl: String?,
    val timeStart: Int,
    val shortName: String?,
    val startTimeString: String
) {

    companion object {
        fun createFromJSON(
            json: JSONObject
        ): TVChannelRelease? {
            val showId = json.extract(
                "showId"
            ) as? Int ?: return null

            val name = json.extract(
                "showName"
            ) as? String ?: return null

            val timeStart = json.extract(
                "timeStart"
            ) as? Int ?: return null

            val censorAge = json.extract(
                "showAgeLimit"
            ) as? Int ?: 4

            val rating = json.extract(
                "showAssessment"
            ) as? Float ?: 0.0f

            val previewUrl = json.extract(
                "previewUrl"
            ) as? String


            return TVChannelRelease(
                showId.toLong(),
                name,
                CensorAge.find(censorAge.toByte()),
                rating,
                previewUrl,
                timeStart,
                if (name.length >= 20)
                    name.substring(0,19) + Unicode.DOTS
                else null,
                timeStart.toTimeString()
            )
        }
    }

}