package com.demo.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity // Enables Spring Security web security support
public class ApplicationSecurityConfig{
    private final PasswordEncoder passwordEncoder;

    @Autowired

    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/","index","/css/*","/js*").permitAll()
                        .requestMatchers("/api/**")
                        .hasRole(ApplicationUserRole.STUDENT.name())
                        .anyRequest()
                        .authenticated()
                )
                .httpBasic(withDefaults());
        return http.build();
    }
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        //password must be encoded
        // If we don't use password encoder to create user as  User.withDefaultPasswordEncoder().....
        //User name should also be unique otherwise would fail to build Error as USer should not exist.
        UserDetails user1 = User.builder()
                .username("user")
                .password(passwordEncoder.encode("password"))
                .roles(ApplicationUserRole.STUDENT.name())  //student
                .build();
        UserDetails user2 = User.builder()
                .username("user2")
                .password(passwordEncoder.encode("password1"))
                .roles(ApplicationUserRole.ADMIN.name()) //admin
                .build();
        UserDetails user3 = User.builder()
                .username("user3")
                .password(passwordEncoder.encode("password1"))
                .roles(ApplicationUserRole.ADMINISTRATE.name())  //admin trainee
                .build();

        return new InMemoryUserDetailsManager(user1,user2);
    }


}