package ie.tozo.repository

import ie.tozo.model.Feed
import org.springframework.data.repository.CrudRepository

interface FeedRepository : CrudRepository<Feed, Int>