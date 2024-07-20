package br.com.joaobarbosadev.wolfcatalogv2.resources;

import br.com.joaobarbosadev.wolfcatalogv2.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryResource {


    @GetMapping
    public ResponseEntity<List<Category>> findAll() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1L, "Tools"));
        categories.add(new Category(2L, "Books"));
        categories.add(new Category(3L, "Computer Science"));
        categories.add(new Category(4L, "Math"));
        categories.add(new Category(5L, "Electronics"));
        return ResponseEntity.ok(categories);
    }
}
