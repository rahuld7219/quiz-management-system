package com.qms.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.qms.auth.filter.JwtAuthenticationFilter;
import com.qms.auth.security.AuthEntryPoint;

import lombok.RequiredArgsConstructor;

/* OLD WAY */

/**
 * @author
 * @version
 * @since
 */
//@Configuration @EnableWebSecurity @RequiredArgsConstructor
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    private final UserDetailsService userDetailsService;
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
//        customAuthenticationFilter.setFilterProcessesUrl("/api/login"); // changes the URL for login from /login to /api/login
//        http.csrf().disable();
//        http.sessionManagement().sessionCreationPolicy(STATELESS);
//        http.authorizeRequests().antMatchers("/api/login/**", "/api/token/refresh/**").permitAll();
//        http.authorizeRequests().antMatchers(GET, "/api/user/**").hasAnyAuthority("ROLE_USER");
//        http.authorizeRequests().antMatchers(POST, "/api/user/save/**").hasAnyAuthority("ROLE_ADMIN");
//        http.authorizeRequests().anyRequest().authenticated();
//        http.addFilter(customAuthenticationFilter);
//        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
//    }
//
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//}

/* NEW WAY */
@Configuration
@RequiredArgsConstructor // for DI for final fields, it creates constructor for final fields and Spring
							// automatically so constructor injection, i.e. find and provide the required
							// objects for the constructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		// securedEnabled = true, // TODO: ??
		// jsr250Enabled = true, // TODO: ??
		prePostEnabled = true)
public class SecurityConfig {

	private static final String ADMIN = "ADMIN";
	private static final String ATTENDEE = "ATTENDEE";

	private final UserDetailsService userDetailsService;

	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	private final AuthEntryPoint unauthorizedHandler;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * 
	 * @return
	 */
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}

	/**
	 * 
	 * @param authConfig
	 * @return
	 * @throws Exception
	 */
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	/**
	 * 
	 * @param http
	 * @return
	 * @throws Exception
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable() // TODO: explore csrf and cors
				.exceptionHandling().authenticationEntryPoint(unauthorizedHandler) // handles Unauthorized request like
																					// if not having required authority
																					// to access an endpoint
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // session will not be
																									// stored
				.and().authorizeRequests()
				.antMatchers("/api/v1/auth/register", "/api/v1/auth/login", "/api/v1/auth/refresh").permitAll()
				.antMatchers("/api/v1/auth/changePassword").hasAnyAuthority(ADMIN, ATTENDEE)
//				.antMatchers("/api/v1/auth/**").permitAll()
				.antMatchers("/api/v1/admin/**").hasAuthority(ADMIN).antMatchers("/api/v1/attendee/**")
				.hasAuthority(ATTENDEE).antMatchers("/api/v1/dummyAdmin/**").hasAnyAuthority(ADMIN).anyRequest()
				.authenticated();

		http.authenticationProvider(authenticationProvider());

		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // add
																									// jwtAuthentication
																									// filter before
																									// UsernamePasswordAuthenticationFilter

		return http.build();

		// TODO: explain use of '.and' and what the way if not used '.and'
	}
}
