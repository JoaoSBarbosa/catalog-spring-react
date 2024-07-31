package br.com.joaobarbosadev.wolfcatalogv2.repositories;

import br.com.joaobarbosadev.wolfcatalogv2.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
