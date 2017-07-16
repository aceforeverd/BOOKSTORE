package org.sjtu.controller;

import org.sjtu.model.AddressEntity;
import org.sjtu.model.BookEntity;
import org.sjtu.model.OrderEntity;
import org.sjtu.model.UserEntity;
import org.sjtu.repository.AddressRepository;
import org.sjtu.repository.OrderRepository;
import org.sjtu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Created by ace on 6/11/17.
 */
@Controller
@RequestMapping(path = "/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AddressRepository addressRepository;

    @GetMapping(path = {"/", ""})
    public @ResponseBody Iterable<UserEntity> getAllUser(@RequestParam(defaultValue = "") String username,
                                                         @RequestParam(defaultValue = "") String email,
                                                         @RequestParam(defaultValue = "false") Boolean fuzzy) {
        if (username.length() == 0 && email.length() ==0) {
            return userRepository.findAll();
        }
        if (username.length() > 0 && email.length() == 0) {
            if (fuzzy) {
                return userRepository.findByUsernameContainingIgnoreCase(username);
            }
            return userRepository.findByUsernameIgnoreCase(username);
        }
        if (username.length() == 0 && email.length() > 0) {
            if (fuzzy) {
                return userRepository.findByEmailContainingIgnoreCase(email);
            }
            return userRepository.findByEmailIgnoreCase(email);
        }
        if (fuzzy) {
            return userRepository.findByUsernameContainingIgnoreCaseAndEmailContainingIgnoreCase(username, email);
        }
        return userRepository.findByUsernameIgnoreCaseAndEmailIgnoreCase(username, email);
    }

    @GetMapping(path = {"/validate", "/validate/"})
    public @ResponseBody ResponseEntity<String> validate(@RequestParam(defaultValue = "") String username,
                                                         @RequestParam(defaultValue = "") String email) {
        if (username.length() == 0 && email.length() == 0) {
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }

        else if (username.length() > 0 && email.length() == 0) {
            String message = userRepository.findByUsernameIgnoreCase(username).size() == 0 ?
                    "OK" : "Conflict";
            return new ResponseEntity<String>(message, HttpStatus.OK);
        }
        else if (username.length() == 0 && email.length() > 0) {
            return new ResponseEntity<String>(
                    userRepository.findByEmailIgnoreCase(email).size() == 0 ? "OK" : "Conflict",
                    HttpStatus.OK);
        }
        else {
            return new ResponseEntity<String>(
                    userRepository.findByUsernameIgnoreCaseAndEmailIgnoreCase(username, email).size() == 0 ?
                            "OK" : "Conflict",
                    HttpStatus.OK);
        }
    }

    @GetMapping(path = "/{id}")
    public @ResponseBody ResponseEntity<UserEntity> getById(@PathVariable Integer id) {
        if (!userRepository.exists(id)) {
            return new ResponseEntity<UserEntity>(HttpStatus.NO_CONTENT);
        }
        UserEntity user = userRepository.findOne(id);
        return new  ResponseEntity<UserEntity>(user, HttpStatus.OK);
    }

    @GetMapping(path = {"/{id}/orders", "/{id}/orders/"})
    public @ResponseBody ResponseEntity<List<OrderEntity>> getOrders(@PathVariable Integer id
    ) {
        if (!userRepository.exists(id)) {
            return new ResponseEntity<List<OrderEntity>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<OrderEntity>>(userRepository.findOne(id).getOrderEntities(), HttpStatus.OK);
    }

    @PostMapping(path = {"/{id}/orders", "/{id}/orders/"})
    public @ResponseBody ResponseEntity<String> placeOrder(@PathVariable Integer id, @RequestBody OrderEntity orderEntity) {
        if (!userRepository.exists(id)) {
            return new ResponseEntity<String>(
                    "{\"errorMsg\": \"No User Found\"}",
                    HttpStatus.BAD_REQUEST
            );
        }

        orderEntity = orderRepository.save(orderEntity);
        UserEntity userEntity = userRepository.findOne(id);
        List<OrderEntity> orderEntities = userEntity.getOrderEntities();
        orderEntities.add(orderEntity);
        userEntity.setOrderEntities(orderEntities);
        userRepository.save(userEntity);
        return new ResponseEntity<String>(
                "{\"message\": \"Saved\"}",
                HttpStatus.OK
        );
    }


    @GetMapping(path = {"/{id}/addrs", "/{id}/addrs/"})
    public @ResponseBody ResponseEntity<List<AddressEntity>> getAddrs(@PathVariable Integer id) {
        if (!userRepository.exists(id)) {
            return new ResponseEntity<List<AddressEntity>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<AddressEntity>>(userRepository.findOne(id).getAddressEntities(), HttpStatus.OK);
    }

    @PostMapping(path = {"/{id}/addrs","/{id}/addrs/"})
    public @ResponseBody ResponseEntity<String> saveAddr(@PathVariable Integer id, @RequestBody AddressEntity addressEntity) {
        if (!userRepository.exists(id)) {
            return new ResponseEntity<String>(
                    "{\"errorMsg\": \"No User Found\"}",
                    HttpStatus.BAD_REQUEST);
        }

        UserEntity userEntity = userRepository.findOne(id);
        addressEntity = addressRepository.save(addressEntity);

        List<AddressEntity> addressEntities = userEntity.getAddressEntities();
        addressEntities.add(addressEntity);
        userEntity.setAddressEntities(addressEntities);
        userRepository.save(userEntity);
        return new ResponseEntity<String>(
                "{\"message\": \"saved\"}",
                HttpStatus.OK
        );
    }
    /*
     @PutMapping(path = {"/{id}/addrs", "/{id}/addrs/"})
     public @ResponseBody ResponseEntity<String> updateAddr(@PathVariable Integer id, @RequestBody AddressEntity addressEntity) {
        if (!userRepository.exists(id)) {
            return new ResponseEntity<String>(
                    "{\"errorMsg\": \"No User Found\"}",
                    HttpStatus.BAD_REQUEST
            );
        }

        UserEntity userEntity = userRepository.findOne(id);

        if (addressEntity.getAddrId() == null) {
            return new ResponseEntity<String>(
                    "{\"errorMsg\": \"No Address Found\"}",
                    HttpStatus.BAD_REQUEST
            );
        }

        List<AddressEntity> addressEntities = userEntity.getAddressEntities();
        Integer index = addressEntities.
     }
*/

    @PostMapping(path = {"/",""}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody
    ResponseEntity<UserEntity> userAdd(@RequestBody UserEntity userEntity) {
        String username = userEntity.getUsername();
        String email = userEntity.getEmail();
        if (userRepository.findByUsernameIgnoreCase(username).size() >= 1 ||
                userRepository.findByEmailIgnoreCase(email).size() >= 1) {
            return new ResponseEntity<UserEntity>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<UserEntity>(userRepository.save(userEntity), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public @ResponseBody ResponseEntity<String> userDel(@PathVariable Integer id) {
        if (!userRepository.exists(id)) {
            return new ResponseEntity<String>("{\"errorMsg\": \"User Not Found\"}",
                    HttpStatus.BAD_REQUEST);
        }
        userRepository.delete(id);
        return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(path = {"/",""}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseEntity<UserEntity> userUpdate(@RequestBody UserEntity userEntity) {

        return new ResponseEntity<UserEntity>(userRepository.save(userEntity), HttpStatus.OK);
    }

    @PostMapping(path = "/login")
    public @ResponseBody ResponseEntity<UserEntity> login(@RequestParam String username,
                                                      @RequestParam String password) {
        List<UserEntity> list = userRepository.findByUsernameIgnoreCase(username);

        if (list.size() == 1 && list.get(0).getPassword().equals(password)) {
            return new ResponseEntity<UserEntity>(
                    list.get(0),
                    HttpStatus.OK);
        }
        return new ResponseEntity<UserEntity>(
                HttpStatus.UNAUTHORIZED);
    }

}
