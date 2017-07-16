package org.sjtu.repository;

import org.sjtu.model.OrderEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by ace on 6/13/17.
 */
public interface OrderRepository extends CrudRepository<OrderEntity, Integer> {
    List<OrderEntity> findByOrderPriceLessThan(Integer higher);
    List<OrderEntity> findByOrderPriceGreaterThan(Integer lower);
    List<OrderEntity> findByOrderPriceBetween(Integer lower, Integer higher);
}
