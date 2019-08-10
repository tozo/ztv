package ie.tozo.model

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Configuration(

    @Id
    val name: String = "",
    val value: String = "",
    val description: String? = ""
)