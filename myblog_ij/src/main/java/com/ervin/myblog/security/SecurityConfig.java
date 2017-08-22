package com.ervin.myblog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

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
	public void configAuthBuilder(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
		authManagerBuilder.inMemoryAuthentication().withUser("Ervin").password("asd").roles("ADMIN");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
		http.csrf().disable();

//		http.csrf().disable().exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and().formLogin()
//				.successHandler(authSuccess).failureHandler(authFailure).and().authorizeRequests().antMatchers("/login")
//				.permitAll().antMatchers("/posts").authenticated().antMatchers("/addpost").authenticated()
//				.antMatchers("/logout").permitAll();
	}

}
