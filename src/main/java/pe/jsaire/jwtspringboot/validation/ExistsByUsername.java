package pe.jsaire.jwtspringboot.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy =ExistsByUsernameValidation.class )
public @interface ExistsByUsername {

    String message() default "Este usuario ya existe";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
