package org.vld.books.security

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler

@Configuration
@EnableResourceServer
open class ResourceServerConfiguration : ResourceServerConfigurerAdapter() {

    override fun configure(http: HttpSecurity?) {
        http?.anonymous()?.disable()
                ?.requestMatchers()
                ?.antMatchers("/books/**")
                ?.and()?.authorizeRequests()
                ?.antMatchers("/books/**")?.access("hasRole('ADMIN')")
                ?.and()?.exceptionHandling()?.accessDeniedHandler(OAuth2AccessDeniedHandler())
    }

    override fun configure(resources: ResourceServerSecurityConfigurer?) {
        resources?.resourceId("my_rest_api")?.stateless(false)
    }
}