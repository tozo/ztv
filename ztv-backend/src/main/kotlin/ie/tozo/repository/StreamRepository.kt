package ie.tozo.repository

import ie.tozo.model.Stream
import ie.tozo.model.StreamId
import org.springframework.data.repository.CrudRepository

interface StreamRepository : CrudRepository<Stream, StreamId>, StreamCustomRepository