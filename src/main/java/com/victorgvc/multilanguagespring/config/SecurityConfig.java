package com.victorgvc.multilanguagespring.config;

import com.victorgvc.multilanguagespring.auth.CustomAuthenticationFilter;
import com.victorgvc.multilanguagespring.auth.CustomAuthorizationFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
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

    private static final String[] AUTH_WHITELIST = {
        // -- Swagger UI v3 (OpenAPI)
        "/v3/api-docs",
        "/swagger-ui/**",
        "/swagger-ui.html",
        // other public endpoints of your API may be appended to this array
        "/login"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthFilter.setFilterProcessesUrl("/login");
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers(AUTH_WHITELIST).permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST ,"/user").permitAll();
        http.authorizeRequests().antMatchers("/user/**").hasAnyAuthority("USER");
        http.authorizeRequests().antMatchers("/category/**").hasAnyAuthority("USER");
        http.authorizeRequests().antMatchers("/project/**").hasAnyAuthority("USER");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE ,"/user/{id}").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.POST ,"/category").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.PUT ,"/category/{id}").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE ,"/category/**").hasAnyAuthority("ADMIN");
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(customAuthFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
            .antMatchers("/v3/api-docs/**", "/swagger-ui/**");
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}