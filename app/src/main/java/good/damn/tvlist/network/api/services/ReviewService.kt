package good.damn.tvlist.network.api.services

import android.util.Log
import androidx.annotation.WorkerThread
import good.damn.tvlist.App
import good.damn.tvlist.extensions.extractArray
import good.damn.tvlist.models.Result
import good.damn.tvlist.network.NetworkJSONService
import good.damn.tvlist.network.api.models.show.TVUserReview
import org.json.JSONObject

class ReviewService(
    private val mId: Long,
    private val mType: String
): NetworkJSONService(
    App.CACHE_DIR
) {

    companion object {
        private const val TAG = "ReviewService"
    }

    @WorkerThread
    fun getReviews(
        fromCache: Boolean = false
    ): ArrayList<TVUserReview>? {

        val url = "${App.URL}/api/$mType/$mId/reviews"

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

        val reviews = ArrayList<TVUserReview>(
            comments.length()
        )

        for (i in 0..<comments.length()) {
            val comment = TVUserReview.createFromJSON(
                comments.getJSONObject(i)
            ) ?: return null

            reviews.add(
                comment
            )
        }

        return reviews
    }

    @WorkerThread
    fun postReview(
        grade: Byte,
        comment: String = ""
    ): Result<TVUserReview> {
        val response = requestPostJSON(
            "${App.URL}/api/$mType/$mId/reviews",
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


}