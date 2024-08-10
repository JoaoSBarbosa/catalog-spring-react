package br.com.joaobarbosadev.wolfcatalogv2.repositories;

import br.com.joaobarbosadev.wolfcatalogv2.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
