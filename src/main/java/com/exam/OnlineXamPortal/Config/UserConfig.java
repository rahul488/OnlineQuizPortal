package com.exam.OnlineXamPortal.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.exam.OnlineXamPortal.JwtFilter.JwtFilter;
import com.exam.OnlineXamPortal.Services.UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class UserConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer{
	
	@Autowired
	private UserService userDetailsService;
	@Autowired
	private JwtFilter jwtFilter;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
	  auth.userDetailsService(userDetailsService);
		
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}
	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().disable();
		http.csrf().disable()
				.authorizeRequests()
			    .antMatchers("/","/register","/authenticate",
			    		"/getRole/**","/sendotp/**","/change")
			    .permitAll()
			    .antMatchers(HttpMethod.OPTIONS,"/**")
			    .permitAll()
			    .anyRequest()
			    .authenticated()
			    .and().exceptionHandling()
			    .and()
			    .sessionManagement()
			    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
				
				http.
				addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class);	
	}
	
	/*
	 * @Bean public WebMvcConfigurer corsConfigurer() { return new
	 * WebMvcConfigurer(){
	 * 
	 * @Override public void addCorsMappings(CorsRegistry registry) {
	 * registry.addMapping("/*") .allowedHeaders("*") .allowedOriginPatterns("*")
	 * .allowedMethods("*") .allowCredentials(true); } }; }
	 */
}
