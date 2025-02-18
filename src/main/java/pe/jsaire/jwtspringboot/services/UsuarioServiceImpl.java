package pe.jsaire.jwtspringboot.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pe.jsaire.jwtspringboot.entities.Role;
import pe.jsaire.jwtspringboot.entities.Usuario;
import pe.jsaire.jwtspringboot.repositories.RoleRepository;
import pe.jsaire.jwtspringboot.repositories.UsuarioRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario save(Usuario usuario) {

        Optional<Role> optionalRole = roleRepository.findByName("ROLE_USER");

        List<Role> roles = new ArrayList<>();
        optionalRole.ifPresent(roles::add);
        if(usuario.isAdmin()){
            Optional<Role> optionalRoleAdmin = roleRepository.findByName("ROLE_ADMIN");
            optionalRoleAdmin.ifPresent(roles::add);
        }
        usuario.setRoles(roles);
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }
}
