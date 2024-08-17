package br.com.joaobarbosadev.wolfcatalogv2.services;

import br.com.joaobarbosadev.wolfcatalogv2.dto.RoleDTO;
import br.com.joaobarbosadev.wolfcatalogv2.dto.UserDTO;
import br.com.joaobarbosadev.wolfcatalogv2.dto.UserInsertDTO;
import br.com.joaobarbosadev.wolfcatalogv2.dto.UserUpdateDTO;
import br.com.joaobarbosadev.wolfcatalogv2.entities.Role;
import br.com.joaobarbosadev.wolfcatalogv2.entities.User;
import br.com.joaobarbosadev.wolfcatalogv2.repositories.RoleRepository;
import br.com.joaobarbosadev.wolfcatalogv2.repositories.UserRepository;
import br.com.joaobarbosadev.wolfcatalogv2.services.exceptions.ControllerDataViolationException;
import br.com.joaobarbosadev.wolfcatalogv2.services.exceptions.ControllerNotFoundException;
import javax.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserService implements UserDetailsService {

    private static Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
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
    public UserDTO insert(UserInsertDTO source) {
        User user = new User();
        copyDtoToEntity(user, source);

        user.setPassword(passwordEncoder(source.getPassword()));

        user = userRepository.save(user);
        return new UserDTO(user);
    }

    @Transactional
    public UserDTO update(UserUpdateDTO source, Long id) {
        try {

            User user = userRepository.getOne(id);

            copyDtoToEntity(user, source);

//            if(source.getPassword() != null) user.setPassword(passwordEncoder(source.getPassword()));

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

    private String passwordEncoder(String password) {
        if (password == null || password.isEmpty()) {
            return "";
        }
        return passwordEncoder.encode(password);
    }

    private void copyDtoToEntity(User destine, UserDTO source) {
        if (source.getEmail() != null) destine.setEmail(source.getEmail());
        if (source.getPhone() != null) destine.setPhone(source.getPhone());
        if (source.getUriImage() != null) destine.setUriImage(source.getUriImage());
        if (source.getFirstName() != null) destine.setFirstName(source.getFirstName());
        if (source.getLastName() != null) destine.setLastName(source.getLastName());

        if (!source.getRoles().isEmpty()) {
            destine.getRoles().clear();

            for (RoleDTO role : source.getRoles()) {
                Role roleEntity = roleRepository.getOne(role.getId());
                destine.getRoles().add(roleEntity);

            }
        }
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);

        if (user == null) {
            log.error("Usuário não encotrado com este email: " + username);
            throw new UsernameNotFoundException("Email  " + username + "' não encontrado");
        }
        log.info("Usuário '"+ user.getUsername()+"' localizado atrvés do email: " + username);
        return user;
    }
}
