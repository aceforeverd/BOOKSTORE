package org.sjtu.repository;

import org.sjtu.model.RoleEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by ace on 7/8/17.
 */
public interface RoleRepository extends CrudRepository<RoleEntity, Integer> {
    List<RoleEntity> findByRoleNameContainingIgnoreCase(String roleName);
}
