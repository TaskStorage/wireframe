package com.taskstorage.wireframe.domain;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "usr")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Username cannot be empty")
    @Length(max = 255, message = "Username too long")
    private String username;

    @NotBlank(message = "Password cannot be empty")
    @Length(max = 255, message = "Password too long")
    private String password;

    private boolean active;

    @Email(message = "Email is not correct")
    @NotBlank(message = "Email cannot be empty")
    private String email;

    private String activationCode;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER) //Формируем таблицу для ролей без создания Ентити + жадная инициализация
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id")) //Описываем имя таблицы с привязкой к таблице usr по полю user_id
    @Enumerated(EnumType.STRING) //Харинм Енам в виде строки
    private Set<Role> roles;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}
    public boolean isActive() {return active;}
    public void setActive(boolean active) {this.active = active;}
    public Set<Role> getRoles() {return roles;}
    public void setRoles(Set<Role> roles) {this.roles = roles;}
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getActivationCode() {
        return activationCode;
    }
    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public boolean isAdmin() {
        return roles.contains(Role.ADMIN);
    }

    @Override
    public boolean isAccountNonExpired() { return true; }
    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() { return isActive(); }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }
}