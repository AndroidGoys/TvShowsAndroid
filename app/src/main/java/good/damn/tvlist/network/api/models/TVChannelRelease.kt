package good.damn.tvlist.network.api.models

import android.util.Log
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

        private const val TAG = "TVChannelRelease"

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

            val rating = (json.extract(
                "showAssessment"
            ) as? Double)?.toFloat() ?: 0.0f

            val previewUrl = json.extract(
                "previewUrl"
            ) as? String

            Log.d(TAG, "createFromJSON: $censorAge")

            return TVChannelRelease(
                showId.toLong(),
                name,
                CensorAge.find(censorAge.toByte()),
                rating,
                previewUrl,
                timeStart,
                if (name.length >= 17)
                    name.substring(0,17) + Unicode.DOTS
                else null,
                timeStart.toTimeString()
            )
        }
    }

}