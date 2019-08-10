package ie.tozo.service.impl

import ie.tozo.model.Stream
import ie.tozo.repository.FeedRepository
import ie.tozo.repository.StreamRepository
import ie.tozo.service.StreamService
import org.springframework.stereotype.Service

@Service
class StreamServiceImpl(
    private val streamRepository: StreamRepository,
    private val feedRepository: FeedRepository
) : StreamService {

    override fun findAll(): List<Stream> {
        val streams = streamRepository.findAll()

        streams.forEach { stream ->
            val feed = feedRepository.findById(stream.feedId)

            feed.ifPresent {
                stream.feedName = it.name
            }
        }

        return streams.toList()
    }

    override fun saveAll(entities: Iterable<Stream>): List<Stream> {
        return streamRepository.saveAll(entities)
    }

    override fun deleteByFeedId(id: Int) {
        return streamRepository.deleteByFeedId(id)
    }

    override fun count(): Long {
        return streamRepository.count()
    }
}