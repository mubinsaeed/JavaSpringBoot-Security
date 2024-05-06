package com.demo.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static com.demo.security.ApplicationUserRole.*;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity // Enables Spring Security web security support
@EnableMethodSecurity(prePostEnabled = true)
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
                        .requestMatchers("/api/**") //if single * means that only the exact api to secure where as ** all the matching after
                        .hasRole(STUDENT.name())

                        //NOTE THIS IS THE BELOW METHOD for authority permission difficult this simpler is using annotation and tag on function level
                       // .requestMatchers(HttpMethod.DELETE,"/management/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
                       // .requestMatchers(HttpMethod.PUT,"/management/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
                       // .requestMatchers(HttpMethod.POST,"/management/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
                       // .requestMatchers(HttpMethod.GET,"/management/**").hasAnyRole(ADMINTRAINEE.name(), ADMIN.name())
                        .anyRequest()
                        .authenticated()
                )
                .httpBasic(withDefaults())
                .csrf(csrf -> csrf.disable());

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
               // .roles(ApplicationUserRole.STUDENT.name())  //ROLE_STUDENT
                .authorities(STUDENT.getGrantedAuthorities())
                .build();
        UserDetails user2 = User.builder()
                .username("user2")
                .password(passwordEncoder.encode("password1"))
                //.roles(ADMIN.name()) //ROLE_ADMIN
                .authorities(ADMIN.getGrantedAuthorities())
                .build();
        UserDetails user3 = User.builder()
                .username("user3")
                .password(passwordEncoder.encode("password1"))
               // .roles(ADMINTRAINEE.name())  //ROLE_ADMINTRAINEE
                .authorities(ADMINTRAINEE.getGrantedAuthorities())
                .build();

        return new InMemoryUserDetailsManager(user1,user2,user3);
    }


}