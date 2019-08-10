package ie.tozo.util

import ie.tozo.exception.ParseException
import ie.tozo.model.Stream
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class UtilsTest {

    @Test
    fun `get full domain with port`() {
        val input = StringBuffer("http://test.com:123/sdf/sdf/sdf")
        assertEquals("http://test.com:123", Utils.getFullDomain(input))
    }

    @Test
    fun `get full domain without port`() {
        val input = StringBuffer("http://test.com/sdf/sdf/sdf")
        assertEquals("http://test.com", Utils.getFullDomain(input))
    }

    @Test
    fun `parse blank m3u`() {
        Utils.parseM3U("")
    }

    @Test
    fun `parse odd number of lines m3u`() {
        assertThrows(ParseException::class.java) {
            Utils.parseM3U("asdasd2123fasf")
        }
    }

    @Test
    fun `name with space`() {
        val result = Utils.parseM3U("#EXTM3U\n#EXTINF:-1,this is a very long name\nurl")
        assertEquals("this is a very long name", result[0].title)
        assertEquals("url", result[0].url)
    }

    @Test
    fun `drop EXTM3U`() {
        val result = Utils.parseM3U("#EXTM3U\n#EXTINF:-1,name\nurl")
        assertEquals("name", result[0].title)
        assertEquals("url", result[0].url)
    }

    @Test
    fun `parse invalid m3u`() {
        assertThrows(NumberFormatException::class.java) {
            Utils.parseM3U("asdasd2123fasf\nurl")
        }
    }

    @Test
    fun `unrecognizable parameter in m3u`() {
        val result = Utils.parseM3U("#EXTINF:-1 smtgparam=\"asdasdasd\",Channel\nurl")
        assertEquals("Channel", result[0].title)
        assertEquals("url", result[0].url)
    }

    @Test
    fun `parse tvgId`() {
        val result = Utils.parseM3U("#EXTM3U\n#EXTINF:-1 tvg-ID=\"tvgId\",name\nurl")
        assertEquals("name", result[0].title)
        assertEquals("url", result[0].url)
        assertEquals("tvgId", result[0].tvgId)
    }

    @Test
    fun `parse tvgShift`() {
        val result = Utils.parseM3U("#EXTM3U\n#EXTINF:-1 tvg-shift=\"12\",name\nurl")
        assertEquals("name", result[0].title)
        assertEquals("url", result[0].url)
        assertEquals(12, result[0].tvgShift)
    }

    @Test
    fun `parse tvgName`() {
        val result = Utils.parseM3U("#EXTM3U\n#EXTINF:-1 tvg-name=\"tvgName\",name\nurl")
        assertEquals("name", result[0].title)
        assertEquals("url", result[0].url)
        assertEquals("tvgName", result[0].tvgName)
    }

    @Test
    fun `parse tvgName with space`() {
        val result = Utils.parseM3U("#EXTM3U\n#EXTINF:-1 tvg-name=\"this is a long tvgName\",name\nurl")
        assertEquals("name", result[0].title)
        assertEquals("url", result[0].url)
        assertEquals("this is a long tvgName", result[0].tvgName)
    }

    @Test
    fun `parse tvgLogo`() {
        val result = Utils.parseM3U("#EXTM3U\n#EXTINF:-1 tvg-logo=\"tvgLogo\",name\nurl")
        assertEquals("name", result[0].title)
        assertEquals("url", result[0].url)
        assertEquals("tvgLogo", result[0].tvgLogo)
    }

    @Test
    fun `parse audioTrack`() {
        val result = Utils.parseM3U("#EXTM3U\n#EXTINF:-1 audio-track=\"audioTrack\",name\nurl")
        assertEquals("name", result[0].title)
        assertEquals("url", result[0].url)
        assertEquals("audioTrack", result[0].audioTrack)
    }

    @Test
    fun `parse aspectRatio`() {
        val result = Utils.parseM3U("#EXTM3U\n#EXTINF:-1 aspect-ratio=\"aspectRatio\",name\nurl")
        assertEquals("name", result[0].title)
        assertEquals("url", result[0].url)
        assertEquals("aspectRatio", result[0].aspectRatio)
    }

    @Test
    fun `parse groupTitle`() {
        val result = Utils.parseM3U("#EXTM3U\n#EXTINF:-1 group-title=\"groupTitle\",name\nurl")
        assertEquals("name", result[0].title)
        assertEquals("url", result[0].url)
        assertEquals("groupTitle", result[0].groupTitle)
    }

    @Test
    fun `parse allParams`() {
        val result = Utils.parseM3U("#EXTM3U\n#EXTINF:-1 tvg-name=\"this is a long tvgName\" aspect-ratio=\"aspectRatio\" tvg-ID=\"tvgId\" tvg-logo=\"https://aso093.dsfkg.dod/aa\" "
                + "  tvg-shift=\"12\" audio-track=\"audioTrack\"   group-title=\"groupTitle\",name\nurl")
        assertEquals("name", result[0].title)
        assertEquals("url", result[0].url)
        assertEquals("this is a long tvgName", result[0].tvgName)
        assertEquals("aspectRatio", result[0].aspectRatio)
        assertEquals("tvgId", result[0].tvgId)
        assertEquals("https://aso093.dsfkg.dod/aa", result[0].tvgLogo)
        assertEquals(12, result[0].tvgShift)
        assertEquals("audioTrack", result[0].audioTrack)
        assertEquals("groupTitle", result[0].groupTitle)
    }

    @Test
    fun `blank filter`() {
        assertFalse(Utils.filterOut("", "ss"))
    }

    @Test
    fun `blank input`() {
        assertFalse(Utils.filterOut("aaa", ""))
    }

    @Test
    fun `filter none found`() {
        assertTrue(Utils.filterOut("test1", "kfgja[sd[P3=sd#d'2-la-tes1"))
    }

    @Test
    fun `filter found`() {
        assertFalse(Utils.filterOut("test1", "kfgja[sd[P3=sd#d'2-la-test1sdf5qwq"))
    }

    @Test
    fun `multiple filters, none found`() {
        assertTrue(Utils.filterOut("test1;test2", "kfgja[sd[P3=sd#d'2-la-tet1sdf5qwq"))
    }

    @Test
    fun `multiple filters, one found`() {
        assertFalse(Utils.filterOut("test1;test2", "kfgja[sd[P3=sd#d'2-la-tet1sdf5qtest2wq"))
    }

    @Test
    fun `set feedId, empty list`() {
        val list = ArrayList<Stream>()
        val result = Utils.setFeedId(1, list)

        assertEquals(0, result.size)
    }

    @Test
    fun `set feedId`() {
        val list = ArrayList<Stream>()
        list.add(Stream())
        val result = Utils.setFeedId(1, list)

        assertEquals(1, result.size)
        assertEquals(1, result[0].feedId)
    }

}