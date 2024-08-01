package br.com.joaobarbosadev.wolfcatalogv2.services;

import br.com.joaobarbosadev.wolfcatalogv2.dto.CategoryDTO;
import br.com.joaobarbosadev.wolfcatalogv2.dto.ProductDTO;
import br.com.joaobarbosadev.wolfcatalogv2.entities.Category;
import br.com.joaobarbosadev.wolfcatalogv2.entities.Product;
import br.com.joaobarbosadev.wolfcatalogv2.repositories.CategoryRepository;
import br.com.joaobarbosadev.wolfcatalogv2.repositories.ProductRepository;
import br.com.joaobarbosadev.wolfcatalogv2.services.exceptions.ControllerDataViolationException;
import br.com.joaobarbosadev.wolfcatalogv2.services.exceptions.ControllerNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(PageRequest pageRequest) {
        Page<Product> products = productRepository.findAll(pageRequest);
//        return  products.map(ProductDTO::new);
        return products.map((product) -> new ProductDTO(product, product.getCategories()));
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ControllerNotFoundException("Não foi localizado registros de produto com o id informado: " + productId));
        return new ProductDTO(product, product.getCategories());
    }

    @Transactional
    public ProductDTO save(ProductDTO dto) {
        Product product = new Product();
        copyDtoToEntity(dto, product);
        product = productRepository.save(product);
        return new ProductDTO(product, product.getCategories());
    }


    @Transactional
    public ProductDTO update(ProductDTO dto, Long productId) {
        try {
            Product product = productRepository.getReferenceById(productId);
            copyDtoToEntity(dto, product);
            product = productRepository.save(product);
            return new ProductDTO(product, product.getCategories());
        } catch (EntityNotFoundException e) {
            throw new ControllerDataViolationException("Não foi localizado registro de produto com o id informado: " + productId);
        }
    }

    @Transactional
    public void delete(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ControllerNotFoundException("Não foi localizado registro de categoria com o id informado" + productId));
        productRepository.delete(product);
    }

    private void copyDtoToEntity(ProductDTO dto, Product entity) {
        if (dto.getName() != null) entity.setName(dto.getName());
        if (dto.getDate() != null) entity.setDate(dto.getDate());
        if (dto.getPrice() != null) entity.setPrice(dto.getPrice());
        if (dto.getDescription() != null) entity.setDescription(dto.getDescription());
        if (dto.getImgUrl() != null) entity.setImgUrl(dto.getImgUrl());
        if (!dto.getCategories().isEmpty()) {
            entity.getCategories().clear();
            for (CategoryDTO categoryDTO : dto.getCategories()) {
                Category category = categoryRepository.getReferenceById(categoryDTO.getId());
                entity.getCategories().add(category);
            }
        }
    }


}
