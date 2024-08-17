package br.com.joaobarbosadev.wolfcatalogv2.dto;

import br.com.joaobarbosadev.wolfcatalogv2.entities.Category;
import br.com.joaobarbosadev.wolfcatalogv2.entities.Product;
import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
public class CategoryDTO implements Serializable {

    private Long id;
    private String name;
    private Set<ProductDTO> productDTOS = new HashSet<>();

    public CategoryDTO() {
    }

    public CategoryDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryDTO(Category entity) {
        id = entity.getId();
        name = entity.getName();
    }

    public CategoryDTO(Category entity, Set<Product> products) {
        this(entity);
        products.forEach(product -> productDTOS.add(new ProductDTO(product)));
    }


}
