package br.com.joaobarbosadev.wolfcatalogv2.services;

import br.com.joaobarbosadev.wolfcatalogv2.dto.CategoryDTO;
import br.com.joaobarbosadev.wolfcatalogv2.entities.Category;
import br.com.joaobarbosadev.wolfcatalogv2.repositories.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        List<Category> categoryList = categoryRepository.findAll();

        return categoryList.stream().map(CategoryDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public CategoryDTO findByID(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        return category.map(CategoryDTO::new).orElse(null);
    }

}
