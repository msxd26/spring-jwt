package pe.jsaire.jwtspringboot.services;

import pe.jsaire.jwtspringboot.entities.Usuario;

import java.util.List;

public interface UsuarioService {

    List<Usuario> findAll();

    //Usuario findById(Long id);

    Usuario save(Usuario usuario);

    Boolean existsByUsername(String username);
}
