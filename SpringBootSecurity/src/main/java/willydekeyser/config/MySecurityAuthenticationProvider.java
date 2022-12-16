package willydekeyser.config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class MySecurityAuthenticationProvider implements AuthenticationProvider {

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		var authRequest = (MySecurityAuthentication) authentication;
		var password = authRequest.getPassword();
		var name = authRequest.getName();
		
		if (!"password".equals(password)) {
			throw new BadCredentialsException("You are not the Developer!");
		}
		return MySecurityAuthentication.authenticated(name);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return MySecurityAuthentication.class.isAssignableFrom(authentication);
	}

}
