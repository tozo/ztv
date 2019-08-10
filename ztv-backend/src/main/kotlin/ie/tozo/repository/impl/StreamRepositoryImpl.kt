package ie.tozo.repository.impl

import ie.tozo.model.Stream
import ie.tozo.repository.StreamCustomRepository
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.simple.SimpleJdbcInsert
import org.springframework.stereotype.Repository
import java.sql.ResultSet
import java.util.*

@Repository
class StreamRepositoryImpl(
    private val namedJdbcTemplate: NamedParameterJdbcTemplate
) : StreamCustomRepository {

    private final val jdbcInsert: SimpleJdbcInsert = SimpleJdbcInsert(namedJdbcTemplate.jdbcTemplate)

    init {
        jdbcInsert.withTableName("STREAM")
    }

    override fun findMaxGuideNumber(): Int {
        return namedJdbcTemplate.query("SELECT max(GUIDE_NUMBER) FROM Stream") { rs: ResultSet, _: Int -> rs.getInt(1) }[0]
    }

    override fun saveAll(entities: Iterable<Stream>): List<Stream> {
        val result = ArrayList<Stream>()
        if (!entities.iterator().hasNext()) {
            return result
        }

        var guideNumber = findMaxGuideNumber()

        val entries = ArrayList<MapSqlParameterSource>()

        entities.forEach {
            val entry = MapSqlParameterSource()
            entry.addValue("FEED_ID", it.feedId)
            entry.addValue("URL", it.url)
            entry.addValue("TITLE", it.title)
            entry.addValue("GUIDE_NUMBER", ++guideNumber)
            entry.addValue("LENGTH", it.length)
            entry.addValue("TVG_ID", it.tvgId)
            entry.addValue("TVG_SHIFT", it.tvgShift)
            entry.addValue("TVG_NAME", it.tvgName)
            entry.addValue("TVG_LOGO", it.tvgLogo)
            entry.addValue("AUDIO_TRACK", it.audioTrack)
            entry.addValue("ASPECT_RATION", it.aspectRatio)
            entry.addValue("GROUP_TITLE", it.groupTitle)
            entries.add(entry)
        }

        jdbcInsert.executeBatch(*entries.toTypedArray())
        return entities.toList()
    }

    override fun deleteByFeedId(id: Int) {
        namedJdbcTemplate.update("DELETE FROM STREAM WHERE FEED_ID = :id", mapOf("id" to id))
    }
}