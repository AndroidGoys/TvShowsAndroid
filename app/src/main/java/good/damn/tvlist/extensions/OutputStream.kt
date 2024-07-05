package good.damn.tvlist.extensions

import java.io.OutputStream

fun OutputStream.writeBytes(
    data: ByteArray
) {
    write(data)
    close()
}