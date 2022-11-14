package willydekeyser.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeRequests(auth -> auth
				.antMatchers("/").permitAll()
				.antMatchers("/user/**").authenticated()
				.antMatchers("/admin/**").denyAll()
			)
			.formLogin() // Login with browser and Form
			.and()
			.httpBasic(); // Login with Insomnia and Basic Auth
		return http.build();
	}
	
}
