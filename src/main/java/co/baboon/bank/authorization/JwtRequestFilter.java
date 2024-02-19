package co.baboon.bank.authorization;

import co.baboon.bank.utilities.JwtUtility;
import io.jsonwebtoken.JwtException;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtRequestFilter extends OncePerRequestFilter {
    private final JwtUtility jwtUtility;
    private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);

    public JwtRequestFilter(JwtUtility jwtUtility) { this.jwtUtility = jwtUtility; }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @Nonnull HttpServletResponse response,
                                    @Nonnull FilterChain filterChain) throws ServletException, IOException {
        var header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith("Bearer ") || header.length() < 8) {
            filterChain.doFilter(request, response);
            return;
        }
        
        var jwt = jwtUtility.getJwtFromAuthorizationHeader(header);
        
        try {
            var userId = jwtUtility.getUserIdFromJwt(jwt);
            var grantedAuthority = new SimpleGrantedAuthority("user");
            var authentication = new UsernamePasswordAuthenticationToken(userId, null, List.of(grantedAuthority));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (JwtException e) {
            logger.warn("Wrong JWT");
        }
        
        filterChain.doFilter(request, response);
    }
}
