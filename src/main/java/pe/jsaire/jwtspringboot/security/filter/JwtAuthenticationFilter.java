package pe.jsaire.jwtspringboot.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pe.jsaire.jwtspringboot.entities.Usuario;


import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import static pe.jsaire.jwtspringboot.security.TokenJwtConfig.*;

public class JwtAuthenticationFilter  extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        Usuario usuario = null;
        String username = null;
        String password = null;

        try {
            usuario = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
            username = usuario.getUsername();
            password = usuario.getPassword();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(usuario.getUsername(), usuario.getPassword());

        return authenticationManager.authenticate(authRequest);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user = (User)authResult.getPrincipal();

        String username = user.getUsername();

        authResult.getAuthorities();
        Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();

        Claims claims= Jwts.claims().add("authorities", roles).build();

        String token = Jwts.builder().subject(username)
                .setClaims(claims)
                .expiration(new Date(System.currentTimeMillis()+3600000))
                .issuedAt(new Date())

                .signWith(SECRET_KEY).compact();

        response.addHeader(HEADER_AUTHORIZATION, PREFIX_TOKEN + token);
        Map<String, String> body = new HashMap<>();
        body.put("token", token);
        body.put("username", username);
        body.put("message",String.format("Hola %s sesion con exito!", username));

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setContentType(CONTENT_TYPE);
        response.setStatus(200);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        Map<String, String> body = new HashMap<>();
        body.put("message", "Error en la autenticacion username o password incorrectos!!");
        body.put("error",failed.getMessage());
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setContentType(CONTENT_TYPE);
        response.setStatus(401);
    }
}
