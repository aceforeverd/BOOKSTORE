package org.sjtu.repository;

import org.springframework.data.repository.CrudRepository;
import org.sjtu.model.UserEntity;

import java.util.List;

/**
 * Created by ace on 6/11/17.
 */
public interface UserRepository extends CrudRepository<UserEntity, Integer> {
    List<UserEntity> findByUsernameIgnoreCase(String username);
    List<UserEntity> findByEmailIgnoreCase(String email);

    // fuzzy search
    List<UserEntity> findByUsernameContainingIgnoreCase(String username);
    List<UserEntity> findByEmailContainingIgnoreCase(String email);
    List<UserEntity> findByUsernameIgnoreCaseAndEmailIgnoreCase(String username, String email);
    List<UserEntity> findByUsernameContainingIgnoreCaseAndEmailContainingIgnoreCase(String username, String email);
}
