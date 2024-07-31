package br.com.joaobarbosadev.wolfcatalogv2.services;

import br.com.joaobarbosadev.wolfcatalogv2.dto.CategoryDTO;
import br.com.joaobarbosadev.wolfcatalogv2.dto.ProductDTO;
import br.com.joaobarbosadev.wolfcatalogv2.entities.Category;
import br.com.joaobarbosadev.wolfcatalogv2.entities.Product;
import br.com.joaobarbosadev.wolfcatalogv2.repositories.ProductRepository;
import br.com.joaobarbosadev.wolfcatalogv2.services.exceptions.ControllerNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(PageRequest pageRequest){
        Page<Product> products = productRepository.findAll(pageRequest);
//        return  products.map(ProductDTO::new);
        return  products.map((product)-> new ProductDTO(product, product.getCategories()));
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long productId){
        Product product = productRepository.findById(productId).orElseThrow(()-> new ControllerNotFoundException("Não foi localizado registros de produto com o id informado: "+ productId));
        return  new ProductDTO(product, product.getCategories());
    }

//    @Transactional
//    public ProductDTO save(ProductDTO dto){
//        Product product = new Product();
//    }
//
//
//
//
//
//    private void copyDtoToEntity(ProductDTO dto, Product entity){
//        if(dto.getName() != null) entity.setName(dto.getName());
//        if(dto.getDate() != null) entity.setDate(dto.getDate());
//        if(dto.getPrice() != null) entity.setPrice(dto.getPrice());
//        if(dto.getDescription() != null) entity.setDescription(dto.getDescription());
//        if(dto.getImgUrl() != null) entity.setImgUrl(dto.getImgUrl());
//        if(!dto.getCategories().isEmpty()){
//
//            List<CategoryDTO> categoryDTOS = dto.getCategories();
//
//            for(CategoryDTO categoryDTO : categoryDTOS){
//                entity.getCategories().add(new Category(categoryDTO));
//            }
//
//
//        }
//    }
}
