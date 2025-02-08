package com.example.demo.features.users.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.features.roles.models.Permission;
import com.example.demo.features.roles.models.Role;
import com.example.demo.features.users.models.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetailsImpl implements UserDetails {

    public static UserDetailsImpl build(User user) {
        Collection<GrantedAuthority> authorities = getGrantedAuthorities(user);

        return new UserDetailsImpl(user.getId(), user.getPassword(),
                user.getEmail(), user.getPhone(), user.getFirstName(),
                user.getLastName(), authorities);
    }

    private static Collection<String> getPermissions(Set<Role> roles) {
        Collection<String> permissions = new ArrayList<>();
        for (Role role : roles) {
            permissions.add(role.getName());
            for (Permission permission : role.getPermissions()) {
                permissions.add(permission.getName().name());
            }
        }
        return permissions;
    }

    private static Collection<GrantedAuthority> getGrantedAuthorities(User user) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        Collection<String> permissions = getPermissions(user.getRoles());
        for (String permission : permissions) {
            authorities.add(new SimpleGrantedAuthority(permission));
        }
        return authorities;
    }

    private Long id;

    private String password;

    private String email;

    private String username;

    private String phone;

    private String firstName;

    private String lastName;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long id, String password, String email,
            String phone, String firstName, String lastName,
            Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.username = email;
        this.phone = phone;
        this.firstName = firstName;
        this.lastName = lastName;
        this.authorities = authorities;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}
