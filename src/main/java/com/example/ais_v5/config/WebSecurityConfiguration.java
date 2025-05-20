package com.example.ais_v5.config;

import com.example.ais_v5.security.SecurityUserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfiguration {


    private final SecurityUserService securityUserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        authorize ->
                                authorize
//
//                        .requestMatchers(HttpMethod.GET, "/api/v1/grades/grades").hasAnyAuthority("ROLE_STUDENT","ROLE_TEACHER", "ROLE_ADMIN")
//                        .requestMatchers(HttpMethod.GET, "/api/v1/grades/gradesByUserId").hasAnyAuthority("ROLE_STUDENT","ROLE_TEACHER", "ROLE_ADMIN")
//                        .requestMatchers(HttpMethod.GET, "/api/v1/grades/subject/{subject}").hasAnyAuthority("ROLE_STUDENT","ROLE_TEACHER", "ROLE_ADMIN")
//
//
//                        .requestMatchers("/api/v1/grades/**").hasAnyAuthority("ROLE_TEACHER", "ROLE_ADMIN")
//                        .requestMatchers("/api/v1/subjectgroup/**").hasAnyAuthority("ROLE_TEACHER", "ROLE_ADMIN")
//
//
//                        .requestMatchers("/**").hasAuthority("ROLE_ADMIN")


                                        .anyRequest().authenticated()
                )
                .httpBasic(withDefaults())
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/swagger-ui/**", "/v3/api-docs*/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(securityUserService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }


}
