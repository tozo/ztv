package ie.tozo.controller

import ie.tozo.model.DiscoverData
import ie.tozo.util.Utils
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import javax.servlet.http.HttpServletRequest

@Controller
class WebController {

    @GetMapping("/")
    fun index(): String {
        return "index.html"
    }

    @RequestMapping("/api/device.xml", "/api/lineup.post")
    fun device(
        @RequestParam("scan", defaultValue = "start") scan: String,
        @RequestParam("source", defaultValue = "Cable") source: String,
        model: Model, request: HttpServletRequest
    ): String {
        val discoverData = DiscoverData(10, Utils.getFullDomain(request.requestURL))
        model.addAttribute("data", discoverData)
        return "device.xml"
    }

    @GetMapping("/api/devices")
    fun devices(model: Model, request: HttpServletRequest): String {
        val discoverData = DiscoverData(10, Utils.getFullDomain(request.requestURL))
        model.addAttribute("data", discoverData)
        return "device.xml"
    }
}