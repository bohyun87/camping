package com.camping.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		//로그인 설정
		
		//1. 페이지 접근권한
		http.authorizeHttpRequests(authorize -> authorize
					//모든사용자 로그인없잉 접근
					.requestMatchers("/css/**", "/js/**", "/img/**", "/images/**", "/fonts/**","/fontawesome-free-6.3.0-web/**").permitAll()
					.requestMatchers("/", "/members/**", "/camping/**").permitAll()
					.requestMatchers("/favicon.ico", "/error").permitAll()
					//admin 으로 시작하는 경로 관리자만 접근
					.requestMatchers("/admin/**").hasRole("ADMIN")
					//그 외 페이지->로그인 후 접근
					.anyRequest().authenticated()
					)
		
		//2. 로그인 설정
			.formLogin(formLogin -> formLogin
					.loginPage("/members/login")
					.defaultSuccessUrl("/")
					.usernameParameter("email")
					.failureUrl("/members/login/error")
					)		
		
		//3. 로그아웃 설정
			.logout(logout -> logout
					.logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
					.logoutSuccessUrl("/")
					)			
		
		//4. 비인증자 리소스에 접근했을때 설정
			.exceptionHandling(handling -> handling
					.authenticationEntryPoint(new CustomAuthenticationEntryPoint())
					)
			.rememberMe(Customizer.withDefaults());
		
		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
