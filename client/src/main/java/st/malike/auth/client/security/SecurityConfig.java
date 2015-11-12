/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package st.malike.auth.client.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

/**
 *
 * @author malike_st
 */
@Configuration
@EnableResourceServer
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${app.id}")
    private String appId;
    @Value("${app.client.secret}")
    private String appSecret;
    @Value("${auth.server.host}")
    private String authServerHost;
    @Value("${auth.server.port}")
    private int authServerPort;

    @Bean
    public ResourceServerTokenServices tokenService() {
        RemoteTokenServices tokenServices = new RemoteTokenServices();
        tokenServices.setClientId(appId);
        tokenServices.setClientSecret(appSecret);
        tokenServices.setCheckTokenEndpointUrl("http://" + authServerHost + ":" + authServerPort + "/oauth/check_token");
        return tokenServices;
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        OAuth2AuthenticationManager authenticationManager = new OAuth2AuthenticationManager();
        authenticationManager.setTokenServices(tokenService());
        return authenticationManager;
    }
}
