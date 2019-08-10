package ie.tozo.repository

import ie.tozo.model.Configuration
import org.springframework.data.repository.CrudRepository

interface ConfigurationRepository : CrudRepository<Configuration, String>