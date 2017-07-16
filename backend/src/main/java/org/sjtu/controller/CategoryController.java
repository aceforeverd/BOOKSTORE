package org.sjtu.controller;

import org.sjtu.model.BookEntity;
import org.sjtu.model.CategoryEntity;
import org.sjtu.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Created by ace on 6/11/17.
 */
@Controller
@RequestMapping(path = "/categories")
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping(path = {"/", ""})
    public @ResponseBody Iterable<CategoryEntity> categoryAdd() {
        return categoryRepository.findAll();
    }

    @GetMapping(path = {"/{id}", "/{id}/"})
    public @ResponseBody ResponseEntity<CategoryEntity> getById(@PathVariable Integer id) {
        if (!categoryRepository.exists(id)) {
            return new ResponseEntity<CategoryEntity>(
                    HttpStatus.NO_CONTENT
            );
        }
        return new ResponseEntity<CategoryEntity>(
                categoryRepository.findOne(id),
                HttpStatus.OK
        );
    }

    @PostMapping(path = {"/", ""})
    public @ResponseBody
    ResponseEntity<CategoryEntity> addCategory(@RequestBody CategoryEntity categoryEntity) {
        return new ResponseEntity<CategoryEntity>(
            categoryRepository.save(categoryEntity), HttpStatus.OK);
    }

    @DeleteMapping(path = {"/{id}"})
    public @ResponseBody ResponseEntity<String> delete(@PathVariable Integer id) {
        if (!categoryRepository.exists(id)) {
            return new ResponseEntity<String>("{\"errorMsg\": \"Category Not Found\"}", HttpStatus.NO_CONTENT);
        }

        categoryRepository.delete(id);
        return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(path = {"/", ""})
    public @ResponseBody ResponseEntity<CategoryEntity> update(@RequestBody CategoryEntity categoryEntity) {
        return new ResponseEntity<CategoryEntity>(
                categoryRepository.save(categoryEntity),
                HttpStatus.OK
        );
    }

    @GetMapping(path = {"/{id}/books", "/{id}/books/"})
    public @ResponseBody ResponseEntity<List<BookEntity>> getBooks(@PathVariable Integer id) {
        if (!categoryRepository.exists(id)) {
            return new ResponseEntity<List<BookEntity>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<BookEntity>>(
                categoryRepository.findOne(id).getBookSet(),
                 HttpStatus.OK);
    }
}
