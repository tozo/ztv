package ie.tozo.config

import ie.tozo.util.Utils
import org.springframework.boot.context.event.ApplicationStartingEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component

@Component
class ApplicationStartup : ApplicationListener<ApplicationStartingEvent> {

    override fun onApplicationEvent(event: ApplicationStartingEvent) {
        Utils.createDir("~/ztv")
    }
}