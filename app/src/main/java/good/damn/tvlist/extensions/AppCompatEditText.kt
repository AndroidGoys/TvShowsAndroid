package good.damn.tvlist.extensions

import android.text.InputType
import androidx.appcompat.widget.AppCompatEditText

fun AppCompatEditText.singleLined() {
    maxLines = 1
    minLines = 1
    inputType = InputType.TYPE_CLASS_TEXT
}