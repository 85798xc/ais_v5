package com.example.ais_v5.security;


import com.example.ais_v5.entity.Authority;
import com.example.ais_v5.repositorys.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class SecurityUserService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository
        .findByUsername(username)
        .map(
            user ->
                User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .authorities(
                        user.getAuthorities().stream()
                            .map(Authority::getAuthority)
                            .map(SimpleGrantedAuthority::new)
                            .toList())
                    .build())
        .orElseThrow(() -> new UsernameNotFoundException("User %s not found".formatted(username)));
  }


}
