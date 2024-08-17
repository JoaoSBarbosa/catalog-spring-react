package br.com.joaobarbosadev.wolfcatalogv2.dto;

import br.com.joaobarbosadev.wolfcatalogv2.entities.Role;
import br.com.joaobarbosadev.wolfcatalogv2.entities.User;
import br.com.joaobarbosadev.wolfcatalogv2.services.validation.UserInsertValid;
import br.com.joaobarbosadev.wolfcatalogv2.services.validation.UserUpdateValid;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@UserUpdateValid
@Setter
@Getter
public class UserUpdateDTO extends UserDTO{

    public UserUpdateDTO() {
        super();
    }

    public UserUpdateDTO(Long id, String firstName, String lastName, String email, String phone, String uriImage) {
        super(id, firstName, lastName, email, phone, uriImage);
    }

    public UserUpdateDTO(User entity) {
        super(entity);
    }

    public UserUpdateDTO(User entity, Set<Role> roles) {
        super(entity, roles);
    }
}
