package ie.tozo.util

import java.net.URL
import java.nio.charset.StandardCharsets
import java.util.*

object HttpUtils {

    fun download(url: String): String {
        val scanner = Scanner(URL(url).openStream(), StandardCharsets.UTF_8.name()).useDelimiter("\\A")
        val result = if (scanner.hasNext()) scanner.next() else ""
        scanner.close()
        return result
    }
}