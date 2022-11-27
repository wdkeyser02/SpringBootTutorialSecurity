package willydekeyser.config;

import static org.springframework.security.config.Customizer.withDefaults;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {


	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.securityMatcher(PathRequest.toH2Console()) //H2 database
			.authorizeHttpRequests(authConfig -> {
				authConfig.requestMatchers(HttpMethod.GET, "/").permitAll();
				authConfig.requestMatchers(HttpMethod.GET, "/user").hasRole("USER");
				authConfig.requestMatchers(HttpMethod.GET, "/admin").hasRole("ADMIN");
				authConfig.anyRequest().authenticated();
			})
			.headers().frameOptions().disable()
			.and()
			.formLogin(withDefaults()) // Login with browser and Build in Form
			.httpBasic(withDefaults()); // Login with Insomnia or Postman and Basic Auth
		return http.build();
	}
	
	@Bean
	JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource) {
		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
		return jdbcUserDetailsManager;
	}
	
	@Bean
    public DataSource getDataSource() {
		return DataSourceBuilder.create()
				.driverClassName("org.h2.Driver")
				.url("jdbc:h2:mem:user")
				.username("willy")
				.password("password")
				.build();
    }
	
//	@Bean
//	DataSource dataSource() {
//		return new EmbeddedDatabaseBuilder()
//			.setType(EmbeddedDatabaseType.H2)
//			.addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
//			.build();
//	}
	
}
