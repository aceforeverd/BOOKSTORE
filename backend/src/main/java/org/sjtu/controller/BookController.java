package org.sjtu.controller;

import org.sjtu.model.BookEntity;
import org.sjtu.model.CategoryEntity;
import org.sjtu.repository.BookRepository;
import org.sjtu.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by ace on 6/11/17.
 */
@Controller
@RequestMapping(path = "/books")
public class BookController {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping(path = {"/", ""}, produces = MediaType.ALL_VALUE, consumes = MediaType.ALL_VALUE)
    public @ResponseBody
    ResponseEntity<BookEntity> bookAdd(@RequestBody BookEntity bookEntity) {
        return new ResponseEntity<BookEntity>(bookRepository.save(bookEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = {"/", ""})
    public @ResponseBody Iterable<BookEntity> getAllBook(@RequestParam(defaultValue = "") String bookName,
                                                         @RequestParam(defaultValue = "-1") Integer lower,
                                                         @RequestParam(defaultValue = "-1") Integer higher,
                                                         @RequestParam(defaultValue = "false") Boolean fuzzy) {
        if (bookName.length() == 0) {
            if (lower < 0 && higher < 0) {
                return bookRepository.findAll();
            }
            else if (lower >= 0 && higher < 0) {
                return bookRepository.findByBookPriceGreaterThan(lower);
            }
            else if (higher >= 0 && lower < 0) {
                return bookRepository.findByBookPriceLessThan(higher);
            }
            else {
                return bookRepository.findByBookPriceBetween(lower, higher);
            }
        }

        else {
            if (lower < 0 && higher < 0) {
                if (fuzzy) {
                    return bookRepository.findByBookNameContainingIgnoreCase(bookName);
                }
                return bookRepository.findByBookNameIgnoreCase(bookName);
            }
            else if (lower >= 0 && higher < 0)  {
                return bookRepository.findByBookNameContainingIgnoreCaseAndBookPriceGreaterThan(bookName, lower);
            }
            else if (lower < 0 && higher >= 0) {
                return bookRepository.findByBookNameContainingIgnoreCaseAndBookPriceLessThan(bookName, higher);
            }
            else {
                return bookRepository.findByBookNameContainingIgnoreCaseAndBookPriceBetween(bookName, lower, higher);
            }
        }

    }

    @GetMapping(path = "/{id}")
    public @ResponseBody ResponseEntity<BookEntity> getById(@PathVariable Integer id) {
        if (!bookRepository.exists(id)) {
            return new ResponseEntity<BookEntity>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<BookEntity>(bookRepository.findOne(id), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public @ResponseBody ResponseEntity<String> bookDel(@PathVariable Integer id) {
        if (!bookRepository.exists(id)) {
            return new ResponseEntity<String>(
                    "{\"errorMsg\": \"Book Not Found\"}",
                    HttpStatus.NO_CONTENT);
        }
        bookRepository.delete(id);
        return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(path = {"/",""}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseEntity<BookEntity> bookUpdate(@RequestBody BookEntity bookEntity) {
        return new ResponseEntity<BookEntity>(bookRepository.save(bookEntity), HttpStatus.OK);
    }

}
