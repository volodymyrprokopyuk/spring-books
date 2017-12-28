package org.vld.books.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.oauth2.provider.ClientDetailsService
import org.springframework.security.oauth2.provider.approval.TokenStoreUserApprovalHandler
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore

@Configuration
@EnableWebSecurity
open class WebSecurityConfiguration : WebSecurityConfigurerAdapter() {

    @Autowired
    private lateinit var clientDetailsService: ClientDetailsService

    @Bean
    open fun tokenStore(): TokenStore = InMemoryTokenStore()

    @Bean
    open fun userApprovalHandler(): UserApprovalHandler {
        val userApprovalHandler = TokenStoreUserApprovalHandler()
        userApprovalHandler.setTokenStore(tokenStore())
        userApprovalHandler.setRequestFactory(DefaultOAuth2RequestFactory(clientDetailsService))
        userApprovalHandler.setClientDetailsService(clientDetailsService)
        return userApprovalHandler
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.inMemoryAuthentication()
                ?.withUser("bill")?.password("abc123")?.roles("ADMIN")
                ?.and()?.withUser("bob")?.password("abc123")?.roles("USER")
    }

    override fun configure(http: HttpSecurity?) {
        http?.csrf()?.disable()
                ?.anonymous()?.disable()
                ?.authorizeRequests()
                ?.antMatchers("/oauth/token")?.permitAll()
    }
}