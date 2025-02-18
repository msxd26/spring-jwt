package pe.jsaire.jwtspringboot.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pe.jsaire.jwtspringboot.entities.Usuario;
import pe.jsaire.jwtspringboot.repositories.UsuarioRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> optionalUsuario = usuarioRepository.findByUsername(username);

        if (optionalUsuario.isEmpty()) {
            throw new UsernameNotFoundException(String.format("Usuario %s no existe en el sistema", username));
        }
        Usuario usuario = optionalUsuario.orElseThrow();

        List<GrantedAuthority> authorities = usuario.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
        return new User(usuario.getUsername(), usuario.getPassword(), authorities);
    }
}
