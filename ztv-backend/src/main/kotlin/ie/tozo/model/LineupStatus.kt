package ie.tozo.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonPropertyOrder(alphabetic = true)
data class LineupStatus(

    @JsonProperty("ScanInProgress") val scanInProgress: Int = 0,
    @JsonProperty("ScanPossible") val scanPossible: Int = 1,
    @JsonProperty("Source") val source: String = "Antenna",
    @JsonProperty("SourceList") val sourceList: List<String> = listOf("Cable", "Antenna")
)