package ie.tozo.service

import ie.tozo.model.Stream

interface StreamService {

    fun findAll(): List<Stream>

    fun saveAll(entities: Iterable<Stream>): List<Stream>

    fun deleteByFeedId(id: Int)

    fun count(): Long
}