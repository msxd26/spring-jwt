package pe.jsaire.jwtspringboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.jsaire.jwtspringboot.entities.Usuario;

import java.util.Optional;

public interface UsuarioRepository  extends JpaRepository<Usuario, Long> {

    Boolean existsByUsername(String username);

    Optional<Usuario> findByUsername(String username);
}
