/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package st.malike.auth.server.util;

import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 *
 *
 * @author malike_st
 */
public class UserAuthenticationToken extends AbstractAuthenticationToken {

    private final Object principal;
    private final Object credentials;
    private final boolean client;

    public UserAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        this.client = false;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    public boolean isClient() {
        return client;
    }

}
