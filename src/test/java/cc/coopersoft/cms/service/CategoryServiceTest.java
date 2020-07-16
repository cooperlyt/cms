package cc.coopersoft.cms.service;

import cc.coopersoft.cms.Application;
import cc.coopersoft.cms.model.Category;
import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
//@ContextConfiguration
@Slf4j
@FixMethodOrder(MethodSorters.JVM)
class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Test
    void categoryCurd() {

        Category category = new Category();
        category.setName("test name");
        category = categoryService.addCategory(null, category);



        assertEquals(categoryService.categories(null).size(), 3);

        category = categoryService.addCategory(1l, category);

        assertEquals(categoryService.categories(1l).size(), 5);

        category.setName("modify");

        category = categoryService.editCategory(category.getId(),category);

        Long id = category.getId();

        assertEquals(categoryService.categories(1l).stream().filter(c -> c.getId().equals(id)).collect(Collectors.toList()).get(0).getName()
                ,"modify");
        categoryService.delCategory(id);

        assertEquals(categoryService.categories(1l).size(), 4);

    }




}