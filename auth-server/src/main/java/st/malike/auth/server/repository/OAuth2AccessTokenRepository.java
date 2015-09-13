/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package st.malike.auth.server.repository;

import java.io.Serializable;
import org.springframework.data.mongodb.repository.MongoRepository;
import st.malike.auth.server.model.OAuth2AuthenticationAccessToken;

/**
 *
 * @author malike_st
 */
public interface OAuth2AccessTokenRepository extends MongoRepository<OAuth2AuthenticationAccessToken, Serializable> {

}
