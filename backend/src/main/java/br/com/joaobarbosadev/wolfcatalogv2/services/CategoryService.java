package br.com.joaobarbosadev.wolfcatalogv2.services;

import br.com.joaobarbosadev.wolfcatalogv2.dto.CategoryDTO;
import br.com.joaobarbosadev.wolfcatalogv2.entities.Category;
import br.com.joaobarbosadev.wolfcatalogv2.repositories.CategoryRepository;
import br.com.joaobarbosadev.wolfcatalogv2.services.exceptions.ControllerDataViolationException;
import br.com.joaobarbosadev.wolfcatalogv2.services.exceptions.ControllerNotFoundException;
import br.com.joaobarbosadev.wolfcatalogv2.services.exceptions.ControllerNullValuesException;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.boot.beanvalidation.IntegrationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
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
        return category.map(CategoryDTO::new).orElseThrow(() -> new ControllerNotFoundException("Não existe registros de categoria com o id informado: " + id));

//        return new CategoryDTO(category.get());
    }

    @Transactional
    public CategoryDTO insert(CategoryDTO categoryDTO) {
        if (categoryDTO.getName() == null) {
            throw new ControllerNullValuesException("O campo 'Nome' é obrigatório!");
        }
        Category entity = new Category();
        entity.setName(categoryDTO.getName());

        entity = categoryRepository.save(entity);
        return new CategoryDTO(entity);
    }

    @Transactional
    public CategoryDTO update(CategoryDTO categoryDTO, Long id) {
        try {
            Category category = categoryRepository.getReferenceById(id);
            category.setName(categoryDTO.getName());

            category = categoryRepository.save(category);
            return new CategoryDTO(category);
        } catch (EntityNotFoundException e) {
            throw new ControllerNotFoundException("Não foi localizado registro de categoria com o id informado: " + id);
        }
    }


    public void delete(Long id) {
        try {
            Category category = categoryRepository.findById(id).orElseThrow(() -> new ControllerNotFoundException("Não foi localizado registro de categoria com o id informado" + id));
            categoryRepository.delete(category);
        } catch (DataIntegrityViolationException e) {
            throw new ControllerDataViolationException("A categoria a qual deseja excluir esta sendo utilizada por outras tabelas");
        }
    }
}
