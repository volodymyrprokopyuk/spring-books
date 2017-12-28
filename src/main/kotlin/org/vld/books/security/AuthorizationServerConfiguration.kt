package org.vld.books.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler
import org.springframework.security.oauth2.provider.token.TokenStore

@Configuration
@EnableAuthorizationServer
open class AuthorizationServerConfiguration : AuthorizationServerConfigurerAdapter() {

    @Autowired
    private lateinit var tokenStore: TokenStore

    @Autowired
    private lateinit var userApprovalHandler: UserApprovalHandler

    @Autowired
    @Qualifier("authenticationManagerBean")
    private lateinit var authenticationManager: AuthenticationManager

    override fun configure(clients: ClientDetailsServiceConfigurer?) {
        clients?.inMemory()
                ?.withClient("trusted-client")
                ?.secret("secret")
                ?.authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
                ?.authorizedGrantTypes("authorization_code", "password", "implicit", "refresh_token")
                ?.scopes("read", "write", "trust")
                ?.accessTokenValiditySeconds(120)
                ?.refreshTokenValiditySeconds(600)
    }

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer?) {
        endpoints
                ?.tokenStore(tokenStore)
                ?.userApprovalHandler(userApprovalHandler)
                ?.authenticationManager(authenticationManager)
    }

    override fun configure(security: AuthorizationServerSecurityConfigurer?) {
        security?.realm("MY_OAUTH_REALM/client")
    }
}