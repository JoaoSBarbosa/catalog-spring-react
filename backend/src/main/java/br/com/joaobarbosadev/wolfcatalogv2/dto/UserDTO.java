package br.com.joaobarbosadev.wolfcatalogv2.dto;

import br.com.joaobarbosadev.wolfcatalogv2.entities.Role;
import br.com.joaobarbosadev.wolfcatalogv2.entities.User;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
public class UserDTO implements Serializable {
    private Long id;
    @NotBlank(message = "O campo 'Primeiro nome' é obrigatorio")
    private String firstName;
    private String lastName;
    @Email(message = "Por favor, insira um e-mail válido")
    @Column(unique = true)
    private String email;
    private String phone;
    private String uriImage;

    private Set<RoleDTO> roles = new HashSet<>();

    public UserDTO() {

    }
    public UserDTO(Long id, String firstName, String lastName, String email,  String phone, String uriImage) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.uriImage = uriImage;
    }

    public UserDTO(User entity) {
        id = entity.getId();
        firstName = entity.getFirstName();
        lastName = entity.getLastName();
        email = entity.getEmail();
        phone = entity.getPhone();
        uriImage = entity.getUriImage();
        entity.getRoles().forEach(role -> roles.add(new RoleDTO(role)));
    }

    public UserDTO(User entity, Set<Role> roles) {
        this(entity);
        roles.forEach(role -> this.roles.add(new RoleDTO(role)));
    }
}
