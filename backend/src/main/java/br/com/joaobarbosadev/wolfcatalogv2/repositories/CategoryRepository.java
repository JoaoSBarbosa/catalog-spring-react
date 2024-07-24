package br.com.joaobarbosadev.wolfcatalogv2.repositories;

import br.com.joaobarbosadev.wolfcatalogv2.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
