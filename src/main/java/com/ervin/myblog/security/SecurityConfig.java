package com.ervin.myblog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationFailure authFailure;

    @Autowired
    private AuthenticationSuccess authSuccess;

    @Autowired
    private EntryPointUnauthorizedHandler unauthorizedHandler;

    @Autowired
    private LogoutSuccess logoutSuccess;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    public void configAuthBuilder(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
//        authManagerBuilder.inMemoryAuthentication().withUser("Ervin").password("asd").roles("ADMIN");
        authManagerBuilder.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                .and().authorizeRequests().antMatchers("/app/**", "/posts", "/post/**").permitAll()
                .anyRequest().authenticated()
                .and().logout().logoutUrl("/logout").logoutSuccessHandler(logoutSuccess);

        //set up the JSON authentication filter
        http.addFilterBefore(getAuthenticationFilter(), JsonAuthenticationFilter.class);
    }

    @Bean
    protected JsonAuthenticationFilter getAuthenticationFilter() throws Exception {
        JsonAuthenticationFilter jsonAuthenticationFilter = new JsonAuthenticationFilter();
        jsonAuthenticationFilter.setAuthenticationManager(this.authenticationManagerBean());
        jsonAuthenticationFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login","POST"));
        jsonAuthenticationFilter.setAuthenticationSuccessHandler(authSuccess);
        jsonAuthenticationFilter.setAuthenticationFailureHandler(authFailure);
        return jsonAuthenticationFilter;
    }

}
