package br.com.joaobarbosadev.wolfcatalogv2.services.validation;

import br.com.joaobarbosadev.wolfcatalogv2.component.exceptions.FieldMessage;
import br.com.joaobarbosadev.wolfcatalogv2.dto.UserInsertDTO;
import br.com.joaobarbosadev.wolfcatalogv2.dto.UserUpdateDTO;
import br.com.joaobarbosadev.wolfcatalogv2.entities.User;
import br.com.joaobarbosadev.wolfcatalogv2.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class UserUpdateValidator implements ConstraintValidator<UserUpdateValid, UserUpdateDTO> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpServletRequest request;

    @Override
    public void initialize(UserUpdateValid constraintAnnotation) {
    }

    @Override
    public boolean isValid(UserUpdateDTO userInsertDTO, ConstraintValidatorContext context) {

        // Verifica se a request e o URI_TEMPLATE_VARIABLES_ATTRIBUTE estão presentes
        @SuppressWarnings("unchecked")
        var uriVars = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        if (uriVars == null || !uriVars.containsKey("id")) {
            return true; // Retorna true para não bloquear a execução em caso de erro na obtenção do ID
        }

        long userId = Long.parseLong(uriVars.get("id"));
        List<FieldMessage> list = new ArrayList<>();

        User user = userRepository.findByEmail(userInsertDTO.getEmail());

        if (user != null && userId != user.getId()) {
            list.add(new FieldMessage("email", "Este e-mail já está sendo utilizado. Por favor, utilize um e-mail diferente."));
        }

        for (FieldMessage fieldMessage : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(fieldMessage.getMessage())
                    .addPropertyNode(fieldMessage.getField())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}
