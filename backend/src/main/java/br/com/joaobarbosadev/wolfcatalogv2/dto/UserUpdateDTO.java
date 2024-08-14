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

}
