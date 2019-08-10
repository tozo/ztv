package ie.tozo.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.thymeleaf.spring5.SpringTemplateEngine
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver
import org.thymeleaf.spring5.view.ThymeleafViewResolver
import org.thymeleaf.templatemode.TemplateMode
import java.nio.charset.StandardCharsets


@EnableWebMvc
@Configuration
class ThymeleafConfiguration : WebMvcConfigurer {

    @Bean
    fun templateEngine(): SpringTemplateEngine {
        val templateEngine = SpringTemplateEngine()
        templateEngine.addTemplateResolver(htmlTemplateResolver())
        templateEngine.addTemplateResolver(xmlTemplateResolver())
        return templateEngine
    }

    @Bean
    fun htmlTemplateResolver(): SpringResourceTemplateResolver {
        val templateResolver = SpringResourceTemplateResolver()
        templateResolver.prefix = "classpath:/static/"
        templateResolver.suffix = ".html"
        templateResolver.templateMode = TemplateMode.HTML
        templateResolver.characterEncoding = StandardCharsets.UTF_8.name()
        templateResolver.resolvablePatterns = setOf("*.html")
        templateResolver.isCacheable = false
        return templateResolver
    }

    @Bean
    fun xmlTemplateResolver(): SpringResourceTemplateResolver {
        val templateResolver = SpringResourceTemplateResolver()
        templateResolver.prefix = "classpath:/xml-templates/"
        templateResolver.suffix = ".xml"
        templateResolver.templateMode = TemplateMode.XML
        templateResolver.characterEncoding = StandardCharsets.UTF_8.name()
        templateResolver.resolvablePatterns = setOf("*.xml")
        templateResolver.isCacheable = false
        templateResolver.forceTemplateMode = true
        return templateResolver
    }

    @Bean
    fun xmlViewResolver(): ThymeleafViewResolver {
        val resolver = ThymeleafViewResolver()
        resolver.templateEngine = templateEngine()
        resolver.contentType = MediaType.TEXT_XML_VALUE
        resolver.characterEncoding = StandardCharsets.UTF_8.name()
        resolver.viewNames = arrayOf("*.xml")
        resolver.order = 10
        return resolver
    }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/css/**")
            .addResourceLocations("classpath:/static/css/")

        registry.addResourceHandler("/js/**")
            .addResourceLocations("classpath:/static/js/")

        registry.addResourceHandler("/img/**")
            .addResourceLocations("classpath:/static/img/")

        registry.addResourceHandler("/favicon.ico")
            .addResourceLocations("classpath:/static/favicon.ico")
    }

}