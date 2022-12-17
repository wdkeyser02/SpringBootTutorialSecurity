package willydekeyser.config.providers;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import willydekeyser.config.MySecurityAuthentication;
import willydekeyser.config.MyUser;

public class MySecurityAuthenticationProvider implements AuthenticationProvider {

	private PasswordEncoder passwordEncoder;
	private final UserDetailsService userDetailsService;
	
	public MySecurityAuthenticationProvider(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
		this.passwordEncoder = new BCryptPasswordEncoder();
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		var authRequest = (MySecurityAuthentication) authentication;
		var password = authRequest.getPassword();
		var username = authRequest.getName();

		if ("Test".equals(username)) {
			return null;
		}
		MyUser user = (MyUser) userDetailsService.loadUserByUsername(username);
        if (!this.passwordEncoder.matches(password.toString(), user.getPassword()) || !user.getUsername().equals(username)) {
			throw new BadCredentialsException("Invalid credentials!");
		}
        System.err.println("MySecurityAuthenticationProvider ");
		return MySecurityAuthentication.authenticated(user);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return MySecurityAuthentication.class.isAssignableFrom(authentication);
	}

}
