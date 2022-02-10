package br.tecprog.aluraflix.unit.categories;

import br.tecprog.aluraflix.categories.CategoriesController;
import br.tecprog.aluraflix.categories.CategoriesRepository;
import br.tecprog.aluraflix.categories.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.util.Optional;

@SpringBootTest
public class CategoryEditionTest {
    @MockBean
    CategoriesRepository categoriesRepository;

    @Autowired
    CategoriesController categoriesController;

    final long categoryId = 1L;
    final Category existingCategory = new Category(categoryId, "a valid title", "aBcD18");
    final Category updatedCategory = new Category(categoryId, "a new title", "D18aBc");

    @Test
    public void validCategoryEditionTest() {
        Mockito.when(categoriesRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));
        Mockito.when(categoriesRepository.save(updatedCategory)).thenReturn(updatedCategory);

        var result = categoriesController.update(categoryId, updatedCategory);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(updatedCategory, result.getBody());
        Mockito.verify(categoriesRepository, Mockito.times(1)).findById(categoryId);
        Mockito.verify(categoriesRepository, Mockito.times(1)).save(updatedCategory);
    }

    @Test
    public void inexistentCategoryEditionTest() {
        Mockito.when(categoriesRepository.findById(categoryId)).thenReturn(Optional.empty());
        var result = categoriesController.update(categoryId, updatedCategory);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        Mockito.verify(categoriesRepository, Mockito.times(1)).findById(categoryId);
        Mockito.verify(categoriesRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    public void categoryChangingIdTest() {
        Mockito.when(categoriesRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));

        var result = categoriesController.update(categoryId, new Category(categoryId + 1, "a valid title", "123abc"));

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        Mockito.verify(categoriesRepository, Mockito.times(1)).findById(categoryId);
        Mockito.verify(categoriesRepository, Mockito.never()).save(Mockito.any());
    }
}
