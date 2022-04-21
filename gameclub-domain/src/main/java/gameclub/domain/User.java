package gameclub.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@MappedSuperclass
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    long id;

    String loginName;

    String name;

    String password;

    String email;

    @ElementCollection(fetch=FetchType.EAGER)
    List<Role> roles;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public User() {
    }

    public User(String loginName, String name, String password, String email, List<Role> roles) {
        this.loginName = loginName;
        this.name = name;
        this.password = password;
        this.email = email;
        this.roles = roles;
    }


}

