package willydekeyser.config;

import java.io.IOException;
import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class MySecurityFilter extends OncePerRequestFilter {

	//private final String HEADER_LOGIN = "x-myfilter-login";
	//private final String HEADER_PASSWORD = "x-myfilter-password";
	
	private final String PARAMETER_LOGIN = "login";
	private final String PARAMETER_PASSWORD = "password";
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		System.out.println("\nBefore filterchain\n");
		
//		if (!Collections.list(request.getHeaderNames()).contains(HEADER_LOGIN) || !Collections.list(request.getHeaderNames()).contains(HEADER_PASSWORD)) {
//			System.out.println("No Headers!");
//			filterChain.doFilter(request, response);
//			return;
//		}
	
		
		if (!Collections.list(request.getParameterNames()).contains(PARAMETER_LOGIN) || !Collections.list(request.getParameterNames()).contains(PARAMETER_PASSWORD)) {
			System.out.println("No Parameters!");
			filterChain.doFilter(request, response);
			return;
		}
		
//		var login = request.getHeader(HEADER_LOGIN);
//		var password = request.getHeader(HEADER_PASSWORD);
//		if (!"Developer".equals(login) || !"password".equals(password)) {
//			System.out.println("Headers: " + login + " - " + password);
//			response.setStatus(HttpStatus.FORBIDDEN.value());
//			response.getWriter().println("You are not the Developer!");
//			return;
//		}

		var login = request.getParameter(PARAMETER_LOGIN);
		var password = request.getParameter(PARAMETER_PASSWORD);
		if (!"Developer".equals(login) || !"password".equals(password)) {
			System.out.println("PARAMETER: " + login + " - " + password);
			response.setStatus(HttpStatus.FORBIDDEN.value());
			response.getWriter().println("You are not the Developer!");
			return;
		}

//		System.out.println("Headers: " + login + " - " + password);
		System.out.println("Parameters: " + login + " - " + password);
		
		
		
		var newContext = SecurityContextHolder.createEmptyContext();
		UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(login, null, AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN", "ROLE_DEVELOPER"));
		newContext.setAuthentication(authReq);
		SecurityContextHolder.setContext(newContext);		
		filterChain.doFilter(request, response);
		
		System.out.println("\nAfter filterchain\n");
		
	}

}
