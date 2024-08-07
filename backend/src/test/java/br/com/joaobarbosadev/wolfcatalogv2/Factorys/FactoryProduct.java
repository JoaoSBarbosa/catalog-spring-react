package br.com.joaobarbosadev.wolfcatalogv2.Factorys;

import br.com.joaobarbosadev.wolfcatalogv2.dto.ProductDTO;
import br.com.joaobarbosadev.wolfcatalogv2.entities.Category;
import br.com.joaobarbosadev.wolfcatalogv2.entities.Product;

import java.time.Instant;

public class FactoryProduct {

    public static Product createProduct() {

        Product product = new Product(
                "PC Gamer Tx",
                "PC Gamer Tx, perfeito para jogos de última geração",
                1680.0,
                "https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/21-big.jpg",
                Instant.now()
        );
        Category category = new Category(1L, "Eletronicos", Instant.now());
        product.getCategories().add(category);
        return product;
    }

    public static ProductDTO createProductDTO() {
        Product product = createProduct();
        return new ProductDTO(product, product.getCategories());
    }
}
