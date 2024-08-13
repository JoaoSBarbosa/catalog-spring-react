package br.com.joaobarbosadev.wolfcatalogv2.repositories;

import br.com.joaobarbosadev.wolfcatalogv2.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
}
