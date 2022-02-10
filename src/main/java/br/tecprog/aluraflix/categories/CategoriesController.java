package br.tecprog.aluraflix.categories;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
public class CategoriesController {

    final CategoriesRepository categoriesRepository;

    public CategoriesController(CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }

    @GetMapping("categories")
    public Iterable<Category> getAll() {
        return categoriesRepository.findAll();
    }

    @GetMapping("categories/{id}")
    public ResponseEntity<Category> getById(@PathVariable final Long id) {
        var maybeCategory = categoriesRepository.findById(id);

        return maybeCategory.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("categories")
    @ResponseStatus(HttpStatus.CREATED)
    public Category create(@RequestBody @Valid final Category newCategory) {
        return categoriesRepository.save(newCategory);
    }

    @Transactional
    @PutMapping("categories/{id}")
    public ResponseEntity<Category> update(@PathVariable final Long id, @RequestBody @Valid final Category categoryData) {
        var maybeCategory = categoriesRepository.findById(id);

        if (maybeCategory.isEmpty()) return ResponseEntity.notFound().build();
        var category = maybeCategory.get();
        if (categoryData.getId() != category.getId()) return ResponseEntity.badRequest().build();

        return ResponseEntity.ok(categoriesRepository.save(categoryData));
    }

    @Transactional
    @DeleteMapping("categories/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable final Long id) {
        categoriesRepository.findById(id).ifPresent(category -> categoriesRepository.deleteById(id));
    }
}
