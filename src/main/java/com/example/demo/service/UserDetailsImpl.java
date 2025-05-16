package com.example.demo.service;

import com.example.demo.model.User;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
public class UserDetailsImpl implements UserDetails {

  private Long id;
  private String password;
  private String email;
  private String username;
  private String phone;
  private String firstName;
  private String lastName;
  private Collection<? extends GrantedAuthority> authorities;

  public UserDetailsImpl(
      Long id,
      String email,
      String password,
      String phone,
      String firstName,
      String lastName,
      Collection<? extends GrantedAuthority> authorities) {
    this.id = id;
    this.email = email;
    this.password = password;
    this.username = email;
    this.phone = phone;
    this.firstName = firstName;
    this.lastName = lastName;
    this.authorities = authorities;
  }

  public static UserDetailsImpl build(User user) {
    Collection<GrantedAuthority> authorities = getGrantedAuthorities(user);

    return new UserDetailsImpl(
        user.getId(),
        user.getEmail(),
        user.getPassword(),
        user.getPhone(),
        user.getFirstName(),
        user.getLastName(),
        authorities);
  }

  private static Collection<GrantedAuthority> getGrantedAuthorities(User user) {
    return user.getRoles().stream()
        .flatMap(
            role ->
                Stream.concat(
                    Stream.of(new SimpleGrantedAuthority(role.getName())),
                    role.getPermissions().stream()
                        .map(permission -> new SimpleGrantedAuthority(permission.getName()))))
        .collect(Collectors.toSet());
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
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserDetailsImpl user = (UserDetailsImpl) o;
    return Objects.equals(id, user.id);
  }
}
