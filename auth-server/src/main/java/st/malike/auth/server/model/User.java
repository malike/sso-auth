/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package st.malike.auth.server.model;

import java.util.Date;
import java.util.List;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author malike_st
 */
@Document(collection = "oauth2_user")
public class User {

    private String id;
    private String email;
    private String password;
    private List<String> rights;
    @CreatedDate
    private Date dateCreated;

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public List<String> getRights() {
        return rights;
    }

    public void setRights(List<String> rights) {
        this.rights = rights;
    }

}
