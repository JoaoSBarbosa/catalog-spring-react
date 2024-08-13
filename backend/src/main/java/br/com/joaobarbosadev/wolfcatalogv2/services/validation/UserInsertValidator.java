package br.com.joaobarbosadev.wolfcatalogv2.services.validation;

import br.com.joaobarbosadev.wolfcatalogv2.component.exceptions.FieldMessage;
import br.com.joaobarbosadev.wolfcatalogv2.dto.UserInsertDTO;
import br.com.joaobarbosadev.wolfcatalogv2.entities.User;
import br.com.joaobarbosadev.wolfcatalogv2.repositories.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


public class UserInsertValidator implements ConstraintValidator<UserInsertValid, UserInsertDTO> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void initialize(UserInsertValid constraintAnnotation) {
    }

    @Override
    public boolean isValid(UserInsertDTO userInsertDTO, ConstraintValidatorContext context) {

        List<FieldMessage> list = new ArrayList<>();

        User user = userRepository.findByEmail(userInsertDTO.getEmail());


        if (user != null) {
            list.add(new FieldMessage("email", "Este e-mail já está cadastrado. Por favor, utilize um e-mail diferente."));
        }


        for (FieldMessage fieldMessage : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(fieldMessage.getMessage()).addPropertyNode(fieldMessage.getField()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}
