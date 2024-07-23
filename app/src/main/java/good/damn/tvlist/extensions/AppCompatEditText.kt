package good.damn.tvlist.extensions

import android.text.InputType
import androidx.appcompat.widget.AppCompatEditText

fun AppCompatEditText.asteriskMask() {
    inputType = inputType or InputType.TYPE_TEXT_VARIATION_PASSWORD
}

fun AppCompatEditText.singleLined() {
    maxLines = 1
    minLines = 1
    inputType = inputType or InputType.TYPE_CLASS_TEXT
}