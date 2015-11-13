/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package st.malike.auth.server.repository;

import java.io.Serializable;
import org.springframework.data.mongodb.repository.MongoRepository;
import st.malike.auth.server.model.User;

/**
 *
 * @author malike_st
 */
public interface UserRepository extends MongoRepository<User, Serializable> {

    public User findByEmail(String email);

}
