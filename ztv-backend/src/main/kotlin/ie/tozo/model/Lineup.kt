package ie.tozo.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonPropertyOrder(alphabetic = true)
data class Lineup(
    @JsonProperty("GuideName") val guideName: String,
    @JsonProperty("GuideNumber") val guideNumber: String,
    @JsonProperty("URL") val url: String,
    @JsonProperty("tvg-id") val tvgID: String?,
    @JsonProperty("tvg-logo") val tvgLogo: String?,
    @JsonProperty("tvg-name") val tvgName: String?
)