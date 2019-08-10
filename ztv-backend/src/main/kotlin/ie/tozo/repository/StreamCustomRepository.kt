package ie.tozo.repository

import ie.tozo.model.Stream

interface StreamCustomRepository {

    fun findMaxGuideNumber(): Int

    fun saveAll(entities: Iterable<Stream>): List<Stream>

    fun deleteByFeedId(id: Int)
}