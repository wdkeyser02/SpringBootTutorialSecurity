package willydekeyser.config;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

public class MySecurityAuthentication implements Authentication {

	private static final long serialVersionUID = 1L;
	private boolean isAuthenticated = true;
	private final List<GrantedAuthority> authorities;
	private final String password;
	private final String name;

	private MySecurityAuthentication(List<GrantedAuthority> authorities, String name, String password) {
		this.authorities = authorities;
		this.password = password;
		this.name = name;
		this.isAuthenticated = password == null;
	}
	
	public static MySecurityAuthentication unauthenticated(String name, String password) {
		return new MySecurityAuthentication(Collections.emptyList(), name, password);
	}
	
	public static MySecurityAuthentication authenticated(String name) {
		return new MySecurityAuthentication(AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN", "ROLE_DEVELOPER"), name, null);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getDetails() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return new MyUser(getName(), "", true, true, true, true, getAuthorities(), "Willy", "De Keyser", "wdkeyser@gmail.com", LocalDate.of(1990, 01, 01));
	}

	@Override
	public boolean isAuthenticated() {
		return isAuthenticated;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		throw new IllegalArgumentException("Don't do this");
	}
	
	public String getPassword() {
		return password;
	}

}
