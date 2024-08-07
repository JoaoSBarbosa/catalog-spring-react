package br.com.joaobarbosadev.wolfcatalogv2.repositories;


import br.com.joaobarbosadev.wolfcatalogv2.Factorys.FactoryProduct;
import br.com.joaobarbosadev.wolfcatalogv2.entities.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

@DataJpaTest
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository productRepository;

    private long existedId;
    private long notExistedId;

    @BeforeEach
    void setUp() throws Exception {
        existedId = 1L;
        notExistedId = 200L;
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdExists(){
        productRepository.deleteById(existedId);

        Optional<Product> product = productRepository.findById(existedId);
        Assertions.assertFalse(product.isPresent());
    }

    @Test
    public void saveShouldPersistWithAutoIncrementIdIsNull(){
        Product product = FactoryProduct.createProduct();
        product.setId(null);

        product = productRepository.save(product);
        Assertions.assertNotNull(product.getId());
        Assertions.assertEquals(product.getId(), 26);
    }
    @Test
    public void returnShuldReturnOptionNotEmptyWhithExistedId(){
        Optional<Product> productOptional = productRepository.findById(existedId);
        Assertions.assertNotNull(productOptional);
    }

    @Test
    public void returnShouldReturnOptionEmptyWhithNotExistedId(){
        Optional<Product> productOptional = productRepository.findById(notExistedId);
        boolean isEmpty = true;
        isEmpty = productOptional.isEmpty();
        Assertions.assertTrue(isEmpty);
    }


}
