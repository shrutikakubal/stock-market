package com.example.users.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	JwtRequestFilter jwtRequestFilter;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	JwtAccessDeniedHandler jwtAccessDeniedHandler;
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}
	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		
		httpSecurity.csrf().disable().cors().disable().sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS).
		and().exceptionHandling().accessDeniedHandler(jwtAccessDeniedHandler).
		authenticationEntryPoint(jwtAuthenticationEntryPoint)
		.and().authorizeRequests().
		antMatchers("/", "/login/**","/authenticate/signin").permitAll().
		antMatchers("/user/signup").permitAll().
	    antMatchers("/user/confirm/**").permitAll().
	    antMatchers("/authenticate/admin").hasRole("admin").
		anyRequest().authenticated().
		and().addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class).
		headers().cacheControl();

	}
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/user/authenticate");
	}}
