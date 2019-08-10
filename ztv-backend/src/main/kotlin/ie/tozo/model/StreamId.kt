package ie.tozo.model

import java.io.Serializable

data class StreamId(val feedId: Int = -1, val url: String = "") : Serializable