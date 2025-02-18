package pe.jsaire.jwtspringboot.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;
import pe.jsaire.jwtspringboot.services.UsuarioService;

@Component
@RequiredArgsConstructor
public class ExistsByUsernameValidation implements ConstraintValidator<ExistsByUsername, String> {

    private final UsuarioService usuarioService;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return !usuarioService.existsByUsername(value);
    }
}
