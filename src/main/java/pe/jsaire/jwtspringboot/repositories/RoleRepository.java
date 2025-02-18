package pe.jsaire.jwtspringboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.jsaire.jwtspringboot.entities.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);

}
