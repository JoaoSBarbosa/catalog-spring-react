package br.com.joaobarbosadev.wolfcatalogv2.dto;

import br.com.joaobarbosadev.wolfcatalogv2.entities.Role;
import br.com.joaobarbosadev.wolfcatalogv2.entities.User;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
public class UserDTO implements Serializable {
    private Long id;
    @NotBlank(message = "O campo 'Primeiro Nome' é obrigatório. Por favor, insira seu primeiro nome.")
    private String firstName;
    @Size(max = 50, message = "O campo 'Sobrenome' não pode ter mais de 50 caracteres.")
    private String lastName;
    @Email(message = "Por favor, insira um endereço de e-mail válido.")
    @NotBlank(message = "O campo 'E-mail' é obrigatório. Por favor, insira seu e-mail.")
    @Column(unique = true)
    private String email;
    @Pattern(regexp = "\\d{10,15}", message = "O campo 'Telefone' deve conter entre 10 e 15 dígitos numéricos.")
    private String phone;
    @URL(message = "Por favor, insira uma URL válida para a imagem.")
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
