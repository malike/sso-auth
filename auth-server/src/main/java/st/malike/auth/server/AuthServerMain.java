/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package st.malike.auth.server;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import st.malike.auth.server.config.CustomMongoDBConvertor;

/**
 *
 * @author malike_st
 */
@Configuration
@EnableAutoConfiguration
@EnableMongoRepositories("st.malike.auth.server.repository")
@ComponentScan
@ImportResource({"classpath*:spring-security-oauth2.xml"})
public class AuthServerMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ApplicationContext context = SpringApplication.run(AuthServerMain.class, args);
    }

    @Autowired
    private CustomMongoDBConvertor customMongoDBConvertor;
    @Autowired
    private MongoDbFactory mongoDbFactory;

    @Bean
    public CustomConversions customConversions() {
        List<Converter<?, ?>> converterList = new ArrayList<>();
        converterList.add(customMongoDBConvertor);
        return new CustomConversions(converterList);
    }

    @Bean
    public MappingMongoConverter mongoConverter() throws Exception {
        MongoMappingContext mappingContext = new MongoMappingContext();
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDbFactory);
        MappingMongoConverter mongoConverter = new MappingMongoConverter(dbRefResolver, mappingContext);
        mongoConverter.setCustomConversions(customConversions());
        return mongoConverter;
    }

    @Bean(autowire = Autowire.BY_NAME, name = "mongoTemplate")
    public MongoTemplate customMongoTemplate() {
        try {
            return new MongoTemplate(mongoDbFactory, mongoConverter()); // a mongotemplate with custom convertor
        } catch (Exception e) {
        }
        return null;
    }

}
