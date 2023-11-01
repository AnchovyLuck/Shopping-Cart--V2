package com.springweb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.springweb.security.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class FormLoginSecurityConfig {
	@Autowired
	private CustomAccessDeniedHandler customAccessDeniedHandler;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}
	
	@Bean
	DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsServiceImpl);
		authProvider.setPasswordEncoder(passwordEncoder());
		
		return authProvider;
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http.authorizeHttpRequests((authorizeRequests) -> authorizeRequests
				.requestMatchers("/", "/login", "/403", "/css/**", "/images/**", "/registration", "/webjars/**").permitAll()
				.requestMatchers("/products").permitAll()
				.anyRequest().authenticated())
				.formLogin((formLogin) -> formLogin
						.usernameParameter("username")
						.passwordParameter("password")
						.loginPage("/login")
						.defaultSuccessUrl("/products").permitAll()
						.failureUrl("/login?error=true").permitAll())
				.logout((logout) -> logout
						.invalidateHttpSession(true)
						.clearAuthentication(true)
						.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
						.logoutSuccessUrl("/login?logout").permitAll())
				.exceptionHandling(excpt -> excpt
						.accessDeniedHandler(customAccessDeniedHandler)
						.accessDeniedPage("/403"));
		http.authenticationProvider(authenticationProvider());
		return http.build();
	}
}
