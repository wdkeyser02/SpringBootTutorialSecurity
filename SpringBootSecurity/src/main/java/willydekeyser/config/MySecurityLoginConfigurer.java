package willydekeyser.config;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import willydekeyser.config.providers.MySecurityAuthenticationProvider;
import willydekeyser.config.providers.TestAuthenticationProvider;

public class MySecurityLoginConfigurer extends AbstractHttpConfigurer<MySecurityLoginConfigurer, HttpSecurity>{

	private final UserDetailsService userDetailsService;
	
	public MySecurityLoginConfigurer(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Override
	public void init(HttpSecurity http) throws Exception {
		http.authenticationProvider(new MySecurityAuthenticationProvider(userDetailsService))
			.authenticationProvider(new TestAuthenticationProvider());
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		var authenticationManager = http.getSharedObject(AuthenticationManager.class);
		http.addFilterBefore(new MySecurityFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class);
	}

}
