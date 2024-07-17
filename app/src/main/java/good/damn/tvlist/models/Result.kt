package good.damn.tvlist.models

import androidx.annotation.StringRes

data class Result<RESULT>(
    val result: RESULT? = null,
    @StringRes val errorStringId: Int = -1
)