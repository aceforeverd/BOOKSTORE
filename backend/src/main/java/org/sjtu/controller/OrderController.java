package org.sjtu.controller;

import org.hibernate.criterion.Order;
import org.sjtu.model.OrderEntity;
import org.sjtu.model.UserEntity;
import org.sjtu.repository.OrderRepository;
import org.sjtu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by ace on 6/13/17.
 */
@Controller
@RequestMapping(path = "/orders")
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping(path = {"/",""})
    public @ResponseBody Iterable<OrderEntity> getAllOrder(
            @RequestParam(defaultValue = "-1") Integer lower,
            @RequestParam(defaultValue = "-1") Integer higher
    ) {
        if (lower < 0 && higher < 0) {
            return orderRepository.findAll();
        }
        else if (lower >=0  && higher < 0 ) {
            return orderRepository.findByOrderPriceGreaterThan(lower);
        }
        else if (lower < 0 && higher >= 0) {
            return orderRepository.findByOrderPriceLessThan(higher);
        }
        return orderRepository.findByOrderPriceBetween(lower, higher);
    }

    @PostMapping(path = {"/",""})
    public @ResponseBody
    ResponseEntity<OrderEntity> orderAdd(@RequestBody OrderEntity newOrder) {
        return new ResponseEntity<OrderEntity>(
                orderRepository.save(newOrder),
                HttpStatus.CREATED
        );
    }

    @GetMapping(path = "/{id}")
    public @ResponseBody
    ResponseEntity<OrderEntity> getById(@PathVariable Integer id) {
        if (!orderRepository.exists(id)) {
            return new ResponseEntity<OrderEntity>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<OrderEntity>(
                orderRepository.findOne(id),
                HttpStatus.OK
        );
    }

    @DeleteMapping(path = "/{id}")
    public @ResponseBody
    ResponseEntity<String> deleteById(@PathVariable Integer id) {
        if (!orderRepository.exists(id)) {
            return new ResponseEntity<String>(
                    "{\"errorMsg\": \"Order Not Found\"}",
                    HttpStatus.NO_CONTENT);
        }
        orderRepository.delete(id);
        return new ResponseEntity<String>(
                HttpStatus.NO_CONTENT
        );
    }

    @PutMapping(path = {"/", ""})
    public @ResponseBody ResponseEntity<OrderEntity> updateOrder(@RequestBody OrderEntity orderEntity) {
        return new ResponseEntity<OrderEntity>(
                orderRepository.save(orderEntity),
                HttpStatus.OK);
    }
}
