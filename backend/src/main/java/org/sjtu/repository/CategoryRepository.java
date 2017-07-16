package org.sjtu.repository;

import org.sjtu.model.CategoryEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by ace on 6/11/17.
 */
public interface CategoryRepository extends CrudRepository<CategoryEntity, Integer> {
}
