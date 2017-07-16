package org.sjtu.controller;

import org.sjtu.model.RoleEntity;
import org.sjtu.model.RoleUserEntity;
import org.sjtu.model.UserEntity;
import org.sjtu.repository.RoleRepository;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by ace on 7/8/17.
 */
@Controller
@RequestMapping(path = "/roles")
public class RoleController {
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping(path = {"/", ""})
    public @ResponseBody Iterable<RoleEntity> get(@RequestParam(defaultValue = "") String roleName) {
        if (roleName.length() > 0) {
            return  roleRepository.findByRoleNameContainingIgnoreCase(roleName);
        }
        return roleRepository.findAll();
    }

    @GetMapping(path = {"/{id}/", "/{id}"})
    public @ResponseBody
    ResponseEntity<RoleEntity> findById(@PathVariable Integer id) {
        if (!roleRepository.exists(id)) {
            return new ResponseEntity<RoleEntity>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<RoleEntity>(
                roleRepository.findOne(id),
                HttpStatus.OK
        );
    }

    @GetMapping(path = {"/{id}/users/", "/{id}/users"})
    public @ResponseBody
    ResponseEntity<List<RoleUserEntity>> getUsers(@PathVariable Integer id) {
        if (!roleRepository.exists(id)) {
            return new ResponseEntity<List<RoleUserEntity>>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<List<RoleUserEntity>>(
                roleRepository.findOne(id).getRoleUserEntities(),
                HttpStatus.OK
        );
    }

    @PostMapping(path = {"/", ""})
    public @ResponseBody ResponseEntity<RoleEntity> add(@RequestBody RoleEntity roleEntity) {
        return new ResponseEntity<RoleEntity>(
                roleRepository.save(roleEntity),
                HttpStatus.CREATED
        );
    }

    @PutMapping(path = {"/", ""})
    public @ResponseBody ResponseEntity<RoleEntity> update(@RequestBody RoleEntity roleEntity) {
        if (!roleRepository.exists(roleEntity.getRoleId())) {
            return new ResponseEntity<RoleEntity>(
                    HttpStatus.BAD_REQUEST
            );
        }

        return new ResponseEntity<RoleEntity>(
                roleRepository.save(roleEntity),
                HttpStatus.OK
        );
    }
}
