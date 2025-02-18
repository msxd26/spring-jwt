package pe.jsaire.jwtspringboot.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.jsaire.jwtspringboot.validation.ExistsByUsername;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column(unique = true)
    //@ExistsByUsername
    private String username;

    @NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_roles", joinColumns = @JoinColumn(name = "usuario_id"),
                                        inverseJoinColumns =@JoinColumn(name = "rol_id"),
                                        uniqueConstraints = {@UniqueConstraint(columnNames = {"usuario_id" , "rol_id"})})
    private List<Role> roles;

    @Transient
    private boolean admin;

    @PrePersist
    public void prePersist() {
        this.enabled =true;
    }


}
