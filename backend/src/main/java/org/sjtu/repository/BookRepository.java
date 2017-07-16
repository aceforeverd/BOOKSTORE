package org.sjtu.repository;

import antlr.collections.impl.LList;
import org.codehaus.groovy.runtime.dgmimpl.arrays.IntegerArrayGetAtMetaMethod;
import org.sjtu.model.BookEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
/**
 * Created by ace on 6/11/17.
 */
public interface BookRepository extends CrudRepository<BookEntity, Integer> {
    List<BookEntity> findByBookNameIgnoreCase(String bookName);
    List<BookEntity> findByBookNameContainingIgnoreCase(String bookName);
    List<BookEntity> findByBookPriceGreaterThan(Integer price);
    List<BookEntity> findByBookPriceLessThan(Integer price);
    List<BookEntity> findByBookPriceGreaterThanAndBookPriceLessThan(Integer lower, Integer higher);
    List<BookEntity> findByBookPriceBetween(Integer lower, Integer higher);
    List<BookEntity> findByBookNameContainingIgnoreCaseAndBookPriceBetween(String bookName, Integer lower, Integer higer);
    List<BookEntity> findByBookNameContainingIgnoreCaseAndBookPriceLessThan(String bookName, Integer higher);
    List<BookEntity> findByBookNameContainingIgnoreCaseAndBookPriceGreaterThan(String bookName, Integer lower);
}
