package com.rrain.jwttokenauth.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "users")
@Getter @Setter @ToString
public class User implements UserDetails {
    @Id private String login;
    private String password;
    private String role;
    private String name;

    public User() {}

    public User(String login, String password, String role, String name) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.name = name;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // todo authorities
        return null;
    }




    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }





    public boolean fullyEq(User other){
        return Objects.equals(login,other.login) &&
            Objects.equals(password,other.password) &&
            Objects.equals(role,other.role);
    }
}
