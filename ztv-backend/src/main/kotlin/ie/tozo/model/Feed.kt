package ie.tozo.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Feed(
    val name: String = "",
    val url: String = "",
    @Id @GeneratedValue
    val id: Int = -1
)