package pe.jsaire.jwtspringboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.jsaire.jwtspringboot.entities.Usuario;

public interface UsuarioRepository  extends JpaRepository<Usuario, Long> {
}
