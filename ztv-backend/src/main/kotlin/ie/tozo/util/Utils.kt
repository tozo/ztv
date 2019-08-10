package ie.tozo.util

import ie.tozo.exception.ParseException
import ie.tozo.model.Stream
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.xml.sax.InputSource
import java.io.BufferedReader
import java.io.File
import java.io.StringReader
import java.net.URL
import java.util.stream.Collectors
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory

object Utils {

    private val logger: Logger = LoggerFactory.getLogger(Utils::class.java)

    private const val EXTINF = "#EXTINF:"
    private const val TVG_SHIFT = "tvg-shift"
    private const val TVG_NAME = "tvg-name"
    private const val TVG_ID = "tvg-ID"
    private const val TVG_LOGO = "tvg-logo"
    private const val AUDIO_TRACK = "audio-track"
    private const val ASPECT_RATIO = "aspect-ratio"
    private const val GROUP_TITLE = "group-title"

    private const val XML_ELEMENT_NAME = "test"

    fun getFullDomain(buffer: StringBuffer): String {
        val requestURL = URL(buffer.toString())
        val port = if (requestURL.port == -1) "" else ":" + requestURL.port
        return requestURL.protocol + "://" + requestURL.host + port
    }

    fun createDir(dir: String) {
        File(dir).mkdirs()
    }

    fun parseM3U(content: String): List<Stream> {
        val result = ArrayList<Stream>()
        var lines: List<String> = ArrayList()

        BufferedReader(StringReader(content)).use {
            lines = it.lines().collect(Collectors.toList())
        }

        // The first line can contain BOM char, so rather than compare it, use contains
        if (lines.isNotEmpty() && lines[0].contains("#EXTM3U")) {
            lines = lines.drop(1)
        }

        try {
            // Let's initialize the builder outside of the loop, it can be slow
            val builder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
            for (i in 0 until lines.size step 2) {
                result.add(parseExtInf(builder, lines[i], lines[i + 1]))
            }
        } catch (ioobe: IndexOutOfBoundsException) {
            throw ParseException("Even number of rows expected")
        }

        return result
    }

    fun setFeedId(feedId: Int, streams: List<Stream>): List<Stream> {
        if (streams.isEmpty()) {
            return streams
        }

        // Let's set the feedId for the streams
        for (res in streams) {
            res.feedId = feedId
        }

        return streams
    }

    private fun parseExtInf(builder: DocumentBuilder, firstLine: String, secondLine: String): Stream {
        val parts = firstLine.split(",")
        var details = parts[0]

        details = details.removePrefix(EXTINF)

        var paramsExist = true
        var length: Int

        try {
            length = details.toInt()
            paramsExist = false
        } catch (nfe: NumberFormatException) {
            logger.info("Couldn't convert details[{}] to int, there must be attributes there. ", details)
            length = details.substringBefore(" ").toInt()
        }

        var tvgId = ""
        var tvgShift = 0
        var tvgName = ""
        var tvgLogo = ""
        var audioTrack = ""
        var aspectRatio = ""
        var groupTitle = ""

        if (paramsExist) {
            // Let's pad out the params so they can be processed as an XML
            val params = "<$XML_ELEMENT_NAME " + details.substringAfter(" ") + " />"

            val inputSource = InputSource(StringReader(params))
            val doc = builder.parse(inputSource)
            doc.documentElement.normalize()
            val nodeMap = doc.getElementsByTagName(XML_ELEMENT_NAME).item(0).attributes

            tvgId = nodeMap.getNamedItem(TVG_ID)?.nodeValue ?: ""
            tvgShift = nodeMap.getNamedItem(TVG_SHIFT)?.nodeValue?.toInt() ?: 0
            tvgName = nodeMap.getNamedItem(TVG_NAME)?.nodeValue ?: ""
            tvgLogo = nodeMap.getNamedItem(TVG_LOGO)?.nodeValue ?: ""
            audioTrack = nodeMap.getNamedItem(AUDIO_TRACK)?.nodeValue ?: ""
            aspectRatio = nodeMap.getNamedItem(ASPECT_RATIO)?.nodeValue ?: ""
            groupTitle = nodeMap.getNamedItem(GROUP_TITLE)?.nodeValue ?: ""
        }

        return Stream(
            secondLine,
            parts[1],
            length,
            tvgId,
            tvgShift,
            tvgName,
            tvgLogo,
            audioTrack,
            aspectRatio,
            groupTitle
        )
    }

    fun filterOut(filters: String, input: String): Boolean {
        if (filters.isEmpty() || input.isEmpty()) {
            return false
        }

        filters.split(";").forEach {
            if (input.contains(it, true)) {
                return false
            }
        }

        return true
    }
}