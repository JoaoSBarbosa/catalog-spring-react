package br.com.joaobarbosadev.wolfcatalogv2.repositories;


import br.com.joaobarbosadev.wolfcatalogv2.entities.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void deleteShouldDeleteObjectWhenIdExists(){
        Long existedId = 1L;
        productRepository.deleteById(existedId);

        Optional<Product> product = productRepository.findById(existedId);
        Assertions.assertFalse(product.isPresent());
    }
}
