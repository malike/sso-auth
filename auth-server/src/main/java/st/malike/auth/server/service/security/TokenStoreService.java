/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package st.malike.auth.server.service.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenStore;
import st.malike.auth.server.model.OAuth2AuthenticationAccessToken;
import st.malike.auth.server.model.OAuth2AuthenticationRefreshToken;
import st.malike.auth.server.repository.OAuth2AccessTokenRepository;
import st.malike.auth.server.repository.OAuth2RefreshTokenRepository;

/**
 *
 * @author malike_st
 */
public class TokenStoreService implements TokenStore {

    @Autowired
    private OAuth2AccessTokenRepository oAuth2AccessTokenRepository;
    @Autowired
    private OAuth2RefreshTokenRepository oAuth2RefreshTokenRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    private final AuthenticationKeyGenerator authenticationKeyGenerator = new DefaultAuthenticationKeyGenerator();

    @Override
    public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
        return readAuthentication(token.getValue());
    }

    @Override
    public OAuth2Authentication readAuthentication(String tokenId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("tokenId").is(tokenId));
        OAuth2AuthenticationAccessToken token = mongoTemplate.findOne(query, OAuth2AuthenticationAccessToken.class, "oauth2_access_token");
        return null == token ? null : token.getAuthentication();
    }

    @Override
    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        OAuth2AuthenticationAccessToken oAuth2AuthenticationAccessToken = new OAuth2AuthenticationAccessToken(token,
                authentication, authenticationKeyGenerator.extractKey(authentication));
        mongoTemplate.save(oAuth2AuthenticationAccessToken);
    }

    @Override
    public OAuth2AccessToken readAccessToken(String tokenId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("tokenId").is(tokenId));
        OAuth2AuthenticationAccessToken token = mongoTemplate.findOne(query, OAuth2AuthenticationAccessToken.class, "oauth2_access_token");
        if (null == token) {
            throw new InvalidTokenException("Token not valid");
        }
        return token.getoAuth2AccessToken();
    }

    @Override
    public void removeAccessToken(OAuth2AccessToken accessToken) {
        Query query = new Query();
        query.addCriteria(Criteria.where("tokenId").is(accessToken.getValue()));
        OAuth2AuthenticationAccessToken token = mongoTemplate.findOne(query, OAuth2AuthenticationAccessToken.class, "oauth2_access_token");
        if (token != null) {
            oAuth2AccessTokenRepository.delete(token);
        }
    }

    @Override
    public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
        oAuth2RefreshTokenRepository.save(new OAuth2AuthenticationRefreshToken(refreshToken, authentication));
    }

    @Override
    public OAuth2RefreshToken readRefreshToken(String accessToken) {
        Query query = new Query();
        query.addCriteria(Criteria.where("tokenId").is(accessToken));
        OAuth2AuthenticationRefreshToken token = mongoTemplate.findOne(query, OAuth2AuthenticationRefreshToken.class, "oauth2_refresh_token");
        return token.getoAuth2RefreshToken();
    }

    @Override
    public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
        Query query = new Query();
        query.addCriteria(Criteria.where("tokenId").is(token.getValue()));
        OAuth2AuthenticationRefreshToken auth2AuthenticationRefreshToken = mongoTemplate.findOne(query, OAuth2AuthenticationRefreshToken.class, "oauth2_refresh_token");
        return auth2AuthenticationRefreshToken.getAuthentication();
    }

    @Override
    public void removeRefreshToken(OAuth2RefreshToken accessToken) {
        Query query = new Query();
        query.addCriteria(Criteria.where("tokenId").is(accessToken.getValue()));
        OAuth2AuthenticationRefreshToken token = mongoTemplate.findOne(query, OAuth2AuthenticationRefreshToken.class, "oauth2_refresh_token");
        if (token != null) {
            oAuth2RefreshTokenRepository.delete(token);
        }
    }

    @Override
    public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
        Query query = new Query();
        query.addCriteria(Criteria.where("refreshToken").is(refreshToken.getValue()));
        OAuth2AuthenticationAccessToken token = mongoTemplate.findOne(query, OAuth2AuthenticationAccessToken.class, "oauth2_access_token");
        if (token != null) {
            oAuth2AccessTokenRepository.delete(token);
        }
    }

    @Override
    public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
        String authenticationId = authenticationKeyGenerator.extractKey(authentication);
        if (null == authenticationId) {
            return null;
        }
        Query query = new Query();
        query.addCriteria(Criteria.where("authenticationId").is(authenticationId));
        OAuth2AuthenticationAccessToken token = mongoTemplate.findOne(query, OAuth2AuthenticationAccessToken.class, "oauth2_access_token");
        return token == null ? null : token.getoAuth2AccessToken();
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("clientId").is(clientId));
        List<OAuth2AuthenticationAccessToken> accessTokens = mongoTemplate.find(query, OAuth2AuthenticationAccessToken.class, "oauth2_access_token");
        return extractAccessTokens(accessTokens);
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("clientId").is(clientId));
        query.addCriteria(Criteria.where("userName").is(userName));
        List<OAuth2AuthenticationAccessToken> accessTokens = mongoTemplate.find(query, OAuth2AuthenticationAccessToken.class, "oauth2_access_token");
        return extractAccessTokens(accessTokens);
    }

    private Collection<OAuth2AccessToken> extractAccessTokens(List<OAuth2AuthenticationAccessToken> tokens) {
        List<OAuth2AccessToken> accessTokens = new ArrayList<>();
        tokens.stream().forEach(token -> {
            accessTokens.add(token.getoAuth2AccessToken());
        });
        return accessTokens;
    }

}
