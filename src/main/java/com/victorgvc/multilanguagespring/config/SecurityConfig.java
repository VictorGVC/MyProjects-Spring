package com.victorgvc.multilanguagespring.config;

import com.victorgvc.multilanguagespring.config.Filter.CustomAuthenticationFilter;
import com.victorgvc.multilanguagespring.config.Filter.CustomAuthorizationFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bcryptEncoder;

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService).passwordEncoder(bcryptEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthFilter.setFilterProcessesUrl("/login");
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeHttpRequests().antMatchers("/login").permitAll();
        http.authorizeHttpRequests().antMatchers("/signup").permitAll();
        http.authorizeHttpRequests().antMatchers("/user/**").hasAnyAuthority("USER");
        http.authorizeHttpRequests().antMatchers("/category/**").hasAnyAuthority("USER");
        http.authorizeHttpRequests().antMatchers("/project/**").hasAnyAuthority("USER");
        http.authorizeHttpRequests().antMatchers(HttpMethod.DELETE ,"/user/{id}").hasAnyAuthority("ADMIN");
        http.authorizeHttpRequests().antMatchers(HttpMethod.POST ,"/category").hasAnyAuthority("ADMIN");
        http.authorizeHttpRequests().antMatchers(HttpMethod.PUT ,"/category/{id}").hasAnyAuthority("ADMIN");
        http.authorizeHttpRequests().antMatchers(HttpMethod.DELETE ,"/category/**").hasAnyAuthority("ADMIN");
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(customAuthFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}