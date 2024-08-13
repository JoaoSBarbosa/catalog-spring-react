package br.com.joaobarbosadev.wolfcatalogv2.dto;

import br.com.joaobarbosadev.wolfcatalogv2.entities.Category;
import br.com.joaobarbosadev.wolfcatalogv2.entities.Product;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
public class ProductDTO implements Serializable {

    private Long id;
    @Size(min = 5, max = 60, message = "O nome do produto deve ter entre 5 e 60 caracteres.")
    @NotBlank(message = "O campo 'Nome do Produto' é obrigatório. Por favor, insira o nome do produto.")
    private String name;
    private String description;
    @Positive(message = "O preço deve ser um valor positivo. Por favor, insira um valor válido.")
    private Double price;
    @URL(message = "Por favor, insira uma URL válida para a imagem.")
    private String imgUrl;

    @PastOrPresent(message = "A data do produto não pode ser futura. Por favor, insira uma data válida.")
    private Instant date;
    private List<CategoryDTO> categories = new ArrayList<>();

    public ProductDTO() {}

    public ProductDTO(Long id, String name, String description, Double price, String imgUrl, Instant date) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
        this.date = date;
    }

    public ProductDTO(Product product) {
        id = product.getId();
        name = product.getName();
        description = product.getDescription();
        price = product.getPrice();
        imgUrl = product.getImgUrl();
        date = product.getDate();
    }

    public ProductDTO (Product product, Set<Category> categories) {
        this(product);

        categories.forEach((category)-> this.categories.add(new CategoryDTO(category)));
    }
}
