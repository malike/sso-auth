/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package st.malike.auth.server.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import st.malike.auth.server.model.OAuth2AuthenticationRefreshToken;

/**
 *
 * @author malike_st
 */

public interface OAuth2RefreshTokenRepository extends MongoRepository<OAuth2AuthenticationRefreshToken, String> {

   
}