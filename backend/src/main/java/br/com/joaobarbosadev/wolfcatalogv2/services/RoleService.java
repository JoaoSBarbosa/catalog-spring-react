package br.com.joaobarbosadev.wolfcatalogv2.services;

import br.com.joaobarbosadev.wolfcatalogv2.dto.RoleDTO;
import br.com.joaobarbosadev.wolfcatalogv2.dto.UserDTO;
import br.com.joaobarbosadev.wolfcatalogv2.entities.Role;
import br.com.joaobarbosadev.wolfcatalogv2.repositories.RoleRepository;
import br.com.joaobarbosadev.wolfcatalogv2.services.exceptions.ControllerDataViolationException;
import br.com.joaobarbosadev.wolfcatalogv2.services.exceptions.ControllerNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional(readOnly = true)
    public RoleDTO findById(Long id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new ControllerNotFoundException("Acesso inexistente"));
        return new RoleDTO(role);
    }

    @Transactional(readOnly = true)
    public Page<RoleDTO> findAll(Pageable pageable) {
        Page<Role> roles = roleRepository.findAll(pageable);
        return roles.map(RoleDTO::new);
    }

    @Transactional
    public RoleDTO save(RoleDTO source) {
        Role role = new Role();

        if (source.getAuthority() != null) role.setAuthority(source.getAuthority());

        role = roleRepository.save(role);
        return new RoleDTO(role);
    }

    @Transactional
    public RoleDTO update(Long id, RoleDTO source) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new ControllerNotFoundException("Acesso inexistente"));

        if (source.getAuthority() != null) role.setAuthority(source.getAuthority());
        role = roleRepository.save(role);
        return new RoleDTO(role);
    }

    @Transactional
    public void delete(Long id) {
        try {
            Role role = roleRepository.findById(id).orElseThrow(() -> new ControllerNotFoundException("Acesso inexistente"));
            roleRepository.delete(role);
        } catch (DataIntegrityViolationException e) {
            throw new ControllerDataViolationException("O acesso a qual deseja excluir esta sendo utilizada por outras tabelas");
        }

    }
}
