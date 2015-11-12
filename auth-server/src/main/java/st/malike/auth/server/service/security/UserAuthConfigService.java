/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package st.malike.auth.server.service.security;

import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import st.malike.auth.server.model.User;
import st.malike.auth.server.service.UserService;

/**
 *
 * @author malike_st
 */
@Component
public class UserAuthConfigService {

    @Autowired
    private UserService userService;

    public User getUser(String email) {
        return userService.findByEmail(email);
    }

    public List<GrantedAuthority> getRights(User user) {
        List<GrantedAuthority> grantedAuthority = new LinkedList<GrantedAuthority>();
        List<String> right = user.getRights();
        if (!right.isEmpty()) {
            for (String r : right) {
                grantedAuthority.add(new SimpleGrantedAuthority(r));
            }
        }
        return grantedAuthority;
    }

}
