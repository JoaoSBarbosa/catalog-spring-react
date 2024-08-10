package br.com.joaobarbosadev.wolfcatalogv2.services;

import br.com.joaobarbosadev.wolfcatalogv2.dto.UserDTO;
import br.com.joaobarbosadev.wolfcatalogv2.entities.Category;
import br.com.joaobarbosadev.wolfcatalogv2.entities.User;
import br.com.joaobarbosadev.wolfcatalogv2.repositories.UserRepository;
import br.com.joaobarbosadev.wolfcatalogv2.services.exceptions.ControllerDataViolationException;
import br.com.joaobarbosadev.wolfcatalogv2.services.exceptions.ControllerNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ControllerNotFoundException("Usuário inexistente"));
        return new UserDTO(user);
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> findAll(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return users.map(UserDTO::new);
    }

    @Transactional
    public UserDTO insert(UserDTO source) {
        User user = new User();
        copyDtoToEntity(user,source);
        user = userRepository.save(user);
        return new UserDTO(user);
    }
    @Transactional
    public UserDTO update(UserDTO source, Long id) {
        try {

            User user = userRepository.getReferenceById(id);

            copyDtoToEntity(user, source);

            user = userRepository.save(user);
            return new UserDTO(user);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(e.getMessage());
        }
    }

    @Transactional
    public void deleteById(Long id) {

        try {
            User user = userRepository.findById(id).orElseThrow(() -> new ControllerNotFoundException("Não foi localizado registro de Usuario com o id informado" + id));
            userRepository.delete(user);
        } catch (DataIntegrityViolationException e) {
            throw new ControllerDataViolationException("O usuario a qual deseja excluir esta sendo utilizada por outras tabelas");
        }

    }

    private void copyDtoToEntity(User destine, UserDTO source) {
        if (source.getEmail() != null) destine.setEmail(source.getEmail());
        if (source.getPhone() != null) destine.setPhone(source.getPhone());
        if (source.getUriImage() != null) destine.setUriImage(source.getUriImage());
        if (source.getFirstName() != null) destine.setFirstName(source.getFirstName());
        if (source.getLastName() != null) destine.setLastName(source.getLastName());
    }



}
