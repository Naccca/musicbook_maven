package com.musicbook.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;
	  
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	};
	  
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
			.authorizeRequests().antMatchers("/artists/delete").hasAnyAuthority("USER").and()
			.authorizeRequests().antMatchers("/artists/update").hasAnyAuthority("USER").and()
			.authorizeRequests().antMatchers("/artists/edit").hasAnyAuthority("USER").and()
			.authorizeRequests().antMatchers("/artists/new").not().hasAnyAuthority("USER").and()
			.authorizeRequests().antMatchers("/artists/create").not().hasAnyAuthority("USER").and()
			.authorizeRequests().antMatchers("/bands/new").hasAnyAuthority("USER").and()
			.authorizeRequests().antMatchers("/bands/create").hasAnyAuthority("USER").and()
			.authorizeRequests().antMatchers("/bands/edit").hasAnyAuthority("USER").and()
			.authorizeRequests().antMatchers("/bands/update").hasAnyAuthority("USER").and()
			.authorizeRequests().antMatchers("/bands/delete").hasAnyAuthority("USER").and()
			.authorizeRequests().anyRequest().permitAll().and()
			.formLogin().loginPage("/login").loginProcessingUrl("/loginAction").permitAll().and()
			.logout().logoutSuccessUrl("/").permitAll().and()
			.csrf().disable();
	}
}






