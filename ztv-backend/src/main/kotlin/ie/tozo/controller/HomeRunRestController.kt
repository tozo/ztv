package ie.tozo.controller

import ie.tozo.model.*
import ie.tozo.repository.ConfigurationRepository
import ie.tozo.repository.FeedRepository
import ie.tozo.service.StreamService
import ie.tozo.util.Constants
import ie.tozo.util.HttpUtils
import ie.tozo.util.Utils
import org.apache.commons.validator.routines.UrlValidator
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.server.ResponseStatusException
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/api")
class HomeRunRestController(
    val feedRepository: FeedRepository,
    val streamService: StreamService,
    val configurationRepository: ConfigurationRepository
) {

    private val logger: Logger = LoggerFactory.getLogger(HomeRunRestController::class.java)

    @GetMapping("/discover.json")
    fun discover(request: HttpServletRequest): DiscoverData {
        logger.info("calling discover.json")
        return DiscoverData(10, Utils.getFullDomain(request.requestURL))
    }

    @GetMapping("/lineup_status.json")
    fun lineupStatus(): LineupStatus {
        logger.info("calling lineup_status.json")
        return LineupStatus()
    }

    @GetMapping("/lineup.json")
    fun lineup(): List<Lineup> {
        logger.info("calling lineup.json")
        val streams = streamService.findAll()

        val filter = configurationRepository.findById(Constants.CONFIG_FILTER)

        val result = ArrayList<Lineup>()
        for (stream in streams) {
            if (filter.isPresent && Utils.filterOut(filter.get().value, stream.title)) {
                continue
            }
            result.add(
                Lineup(
                    stream.title,
                    "id-" + stream.guideNumber,
                    stream.url,
                    stream.tvgId,
                    stream.tvgLogo,
                    stream.tvgName
                )
            )
        }
        return result
    }

    @GetMapping("/feeds")
    fun getFeeds(): List<Feed> {
        logger.info("getting all the feeds")
        return feedRepository.findAll().toList()
    }

    @PostMapping("/feeds")
    fun addFeed(
        @RequestParam name: String,
        @RequestParam url: String,
        @RequestParam("file") file: MultipartFile
    ): ResponseEntity<Unit> {
        logger.info("adding a new feed")

        if (url.isBlank() && file.isEmpty) {
            throw ResponseStatusException(HttpStatus.BAD_GATEWAY, "Url or file is required")
        }

        if (file.isEmpty && !UrlValidator().isValid(url)) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Not valid Url")
        } else if (file.isEmpty && UrlValidator().isValid(url)) {
            logger.info("file is empty, retrieving url")
            try {
                val content = HttpUtils.download(url)
                var streams = Utils.parseM3U(content)
                val savedFeed = feedRepository.save(Feed(name, url))
                streams = Utils.setFeedId(savedFeed.id, streams)
                streamService.saveAll(streams)
            } catch (e: Exception) {
                throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Couldn't save the feed", e)
            }
        } else if (!file.isEmpty) {
            logger.info("file is not empty, using that")
            try {
                val content = String(file.bytes, Charsets.UTF_8)
                var streams = Utils.parseM3U(content)
                val savedFeed = feedRepository.save(Feed(name, file.originalFilename ?: file.name))
                streams = Utils.setFeedId(savedFeed.id, streams)
                streamService.saveAll(streams)
            } catch (e: Exception) {
                throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Couldn't save the feed", e)
            }
        }

        // Return with something so Vuex won't try to parse the empty response as XML
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("/feeds/{id}")
    fun deleteFeed(@PathVariable id: Int): ResponseEntity<Unit> {
        logger.info("deleting the feed[{}]", id)
        streamService.deleteByFeedId(id)
        feedRepository.deleteById(id)

        // Return with something so Vuex won't try to parse the empty response as XML
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/streams")
    fun getStreams(): List<Stream> {
        logger.info("Getting all the streams")
        return streamService.findAll()
    }

    @GetMapping("/status")
    fun getStatus(): Status {
        logger.info("Getting the status")
        val numberOfFeeds = feedRepository.count()
        val streams = streamService.findAll()
        val numberOfStreams = streams.size

        val filter = configurationRepository.findById(Constants.CONFIG_FILTER)

        var numberOfFilteredStreams = 0

        for (stream in streams) {
            if (filter.isPresent && Utils.filterOut(filter.get().value, stream.title)) {
                continue
            }
            numberOfFilteredStreams++
        }

        return Status(numberOfFeeds.toInt(), numberOfStreams, numberOfFilteredStreams)
    }

    @GetMapping("/configs/{name}")
    fun getConfiguration(@PathVariable name: String): Configuration? {
        logger.info("Getting the config:[{}]", name)
        if (name.isBlank()) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Name can't be empty")
        }
        return configurationRepository.findByIdOrNull(name)
    }

    @PostMapping("/configs")
    fun addConfiguration(@RequestBody config: Configuration): ResponseEntity<Unit> {
        logger.info("Saving a new config:[{}]", config)
        configurationRepository.save(config)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

}