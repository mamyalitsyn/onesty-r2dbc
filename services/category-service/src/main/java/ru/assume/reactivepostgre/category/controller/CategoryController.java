package ru.assume.reactivepostgre.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import ru.assume.reactivepostgre.category.model.CategoryDomainManagement;
import ru.assume.reactivepostgre.category.model.CategoryDomainShort;
import ru.assume.reactivepostgre.category.model.CategoryPermissionsDomain;
import ru.assume.reactivepostgre.category.service.CategoryManager;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryManager manager;

    @GetMapping("/categoriesDomain")
    public Flux<CategoryDomainShort> getCategoriesDomain(@RequestParam(value = "userId") String userId) {
        log.info("Getting categories domain by user={}", userId);
        return manager.getCategoriesDomain();
    }

    @PostMapping("/categoriesAdd")
    public Flux<CategoryDomainManagement> createCategories(@RequestBody List<CategoryDomainManagement> categories) {
        log.info("persisting new categories");
        return manager.createCategories(categories);
    }

    @GetMapping("/internal/categories/permissions")
    public Flux<CategoryPermissionsDomain> getCategoriesPermissions() {
        log.info("getting categories permissions");
        return manager.getCategoriesPermissions();
    }

}
