package com.hiwijaya.crud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * @author Happy Indra Wijaya
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/customer/**").hasRole("CUSTOMER")
                .and()
                .authorizeRequests()
                .antMatchers("/book").permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/book/**").hasRole("MANAGE_BOOK")
                .and()
                .formLogin()
                .loginPage("/login")
                .and()
                .logout();

    }

    @Bean
    @Override
    public UserDetailsService userDetailsService(){
        UserDetails user1 = User.withDefaultPasswordEncoder()   // withDefaultPasswordEncoder() not for production!
                .username("admin1")
                .password("secret1")
                .roles("CUSTOMER")
                .build();

        UserDetails user2 = User.withDefaultPasswordEncoder()
                .username("admin2")
                .password("secret2")
                .roles("MANAGE_BOOK")
                .build();


        return new InMemoryUserDetailsManager(user1, user2);
    }
}
