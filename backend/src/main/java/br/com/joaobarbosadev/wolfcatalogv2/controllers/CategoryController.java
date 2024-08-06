package br.com.joaobarbosadev.wolfcatalogv2.controllers;

import br.com.joaobarbosadev.wolfcatalogv2.dto.CategoryDTO;
import br.com.joaobarbosadev.wolfcatalogv2.entities.Category;
import br.com.joaobarbosadev.wolfcatalogv2.services.CategoryService;
import br.com.joaobarbosadev.wolfcatalogv2.services.exceptions.ControllerNullValuesException;
import jakarta.persistence.Id;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

//    @GetMapping
//    public ResponseEntity<Page<CategoryDTO>> findAll(
//            @RequestParam(value = "page", defaultValue = "0") Integer page,
//            @RequestParam(value = "linesPerPage", defaultValue = "20") Integer linesPerPage,
//            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
//            @RequestParam(value = "orderBy", defaultValue = "name") String orderBy
//    ) {
//        PageRequest pageRequest = PageRequest.of(page,linesPerPage, Sort.Direction.valueOf(direction), orderBy);
//        Page<CategoryDTO> categories = categoryService.findAll(pageRequest);
//
//        return ResponseEntity.ok(categories);
//    }

    @GetMapping
    public ResponseEntity<Page<CategoryDTO>> findAll(Pageable pageable) {
        Page<CategoryDTO> categories = categoryService.findAll(pageable);

        return ResponseEntity.ok(categories);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable Long id) {
        CategoryDTO dto = categoryService.findByID(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> saveCategory(@RequestBody CategoryDTO categoryDTO) {
        categoryDTO = categoryService.insert(categoryDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(categoryDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(categoryDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO categoryDTO, @PathVariable Long id) {
        categoryDTO = categoryService.update(categoryDTO, id);
        return ResponseEntity.ok(categoryDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
