//package com.aurionpro.lms.config;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import com.aurionpro.lms.security.JwtAuthenticationFilter;
//
//@Configuration
//@EnableMethodSecurity
//public class SecurityConfig {
//
//	@Autowired
//	private JwtAuthenticationFilter jwtAuthenticationFilter;
//
//	@Bean
//	PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//
//	@Bean
//	AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
//		return configuration.getAuthenticationManager();
//	}
//
//	@Bean
//	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		http
//		.cors(cors -> cors.configurationSource(corsConfigurationSource()))
//		.csrf(csrf -> csrf.disable())
//				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//				.authorizeHttpRequests(auth -> auth.requestMatchers("/api/users/registerUser", "/api/users/login")
//						.permitAll().requestMatchers(HttpMethod.GET, "/api/users/getUserById/**")
//						.hasAnyRole("ADMIN", "LOAN_OFFICER", "CUSTOMER").requestMatchers("/api/admin/**")
//						.hasRole("ADMIN").requestMatchers(HttpMethod.PUT, "/api/customers/*/soft-delete")
//						.hasRole("LOAN_OFFICER").requestMatchers(HttpMethod.PUT, "/api/customers/me/soft-delete")
//						.hasRole("CUSTOMER").requestMatchers(HttpMethod.POST, "/api/loans/apply").hasRole("CUSTOMER")
//						.requestMatchers(HttpMethod.PUT, "/api/loans/*/status").hasRole("LOAN_OFFICER")
//						.requestMatchers("/api/loan-schemes/**").hasRole("ADMIN").anyRequest().authenticated())
//				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//
//		return http.build();
//	}
//	
//	@Bean
//	CorsConfigurationSource corsConfigurationSource() {
//		CorsConfiguration config = new CorsConfiguration();
//
//		config.setAllowedOrigins(List.of("http://localhost:4200"));  
//		config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//		config.setAllowedHeaders(List.of("*"));
//
//		config.setAllowCredentials(true);  
//
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		source.registerCorsConfiguration("/**", config);
//		return source;
//	}
//
//}




package com.aurionpro.lms.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.aurionpro.lms.security.JwtAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/users/registerUser", "/api/users/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/users/getUserById/**").hasAnyRole("ADMIN", "LOAN_OFFICER", "CUSTOMER")
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/customers/*/soft-delete").hasRole("LOAN_OFFICER")
                .requestMatchers(HttpMethod.PUT, "/api/customers/me/soft-delete").hasRole("CUSTOMER")
                .requestMatchers(HttpMethod.POST, "/api/loans/apply").hasRole("CUSTOMER")
                .requestMatchers(HttpMethod.PUT, "/api/loans/*/status").hasRole("LOAN_OFFICER")
                .requestMatchers(HttpMethod.GET, "/api/loan-schemes/all").hasAnyRole("CUSTOMER", "ADMIN")
                .requestMatchers("/api/loan-schemes/**").hasRole("ADMIN")
                .anyRequest().authenticated())
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of("http://localhost:4200"));  
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);  

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}