package com.liwanag.jpasecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource datasource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        auth.jdbcAuthentication().dataSource(datasource).withDefaultSchema().withUser(User.withUsername("user").password(encoder.encode("user")).roles("USER"))
                .withUser(User.withUsername("admin").password(encoder.encode("admin")).roles("ADMIN"));
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .antMatchers("/create-user").hasRole("ADMIN")
                .antMatchers("/user/**").hasAnyRole("USER","ADMIN")
                .antMatchers("/users").hasAnyRole("USER","ADMIN")
                .antMatchers("/greeting").permitAll()
                .antMatchers("/h2-console/**").permitAll().and().httpBasic();
        httpSecurity.csrf().disable();
        httpSecurity.headers().frameOptions().disable();
    }
}