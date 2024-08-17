package br.com.joaobarbosadev.wolfcatalogv2.dto;

import br.com.joaobarbosadev.wolfcatalogv2.entities.Role;
import br.com.joaobarbosadev.wolfcatalogv2.entities.User;
import br.com.joaobarbosadev.wolfcatalogv2.services.validation.UserInsertValid;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@UserInsertValid
@Setter
@Getter
public class UserInsertDTO extends UserDTO{
    private String password;

    public UserInsertDTO() {
        super();
    }
    public UserInsertDTO(Long id, String firstName, String lastName, String email, String phone, String uriImage, String password) {
        super(id, firstName, lastName, email, phone, uriImage);
        this.password = password;
    }

    public UserInsertDTO(User entity) {
        super(entity);
    }


    public UserInsertDTO(User entity, Set<Role> roles) {
        super(entity, roles);
    }

}
