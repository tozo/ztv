package ie.tozo.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonPropertyOrder(alphabetic = true)
data class DiscoverData(@JsonProperty("TunerCount") val tunerCount: Int, @JsonProperty("BaseURL") val baseURL: String) {

    @JsonProperty("FriendlyName") val friendlyName = "ZTV"
    @JsonProperty("Manufacturer") val manufacturer = "Tozo LTD"
    @JsonProperty("ModelNumber") val modelNumber = "HDTC-2US"
    @JsonProperty("FirmwareName") val firmwareName = "hdhomeruntc_atsc"
    @JsonProperty("FirmwareVersion") val firmwareVersion = "20190526"
    @JsonProperty("DeviceID") val deviceID = "123456789"
    @JsonProperty("DeviceAuth") val deviceAuth = "tozo1234"
    val lineupURL
        @JsonProperty("LineupURL") get() = "$baseURL/lineup.json"
}