package willydekeyser;

import javax.sql.DataSource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

@SpringBootApplication
public class SpringBootSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSecurityApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(JdbcUserDetailsManager jdbcUserDetailsManager, DataSource dataSource) {
		return args -> {
			UserDetails user = User.builder()
					.username("user")
					.password("{noop}password")
					.roles("USER")
					.build();
				UserDetails admin = User.builder()
					.username("admin")
					.password("{noop}password")
					.roles("USER", "ADMIN")
					.build();
				JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
				users.createUser(user);
				users.createUser(admin);
		};
	}
}
