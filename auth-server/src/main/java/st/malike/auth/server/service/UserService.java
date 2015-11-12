/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package st.malike.auth.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import st.malike.auth.server.model.User;
import st.malike.auth.server.repository.UserRepository;

/**
 *
 * @author malike_st
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    public User getUserById(String id) {
        return userRepository.findOne(id);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }

}
