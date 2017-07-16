package org.sjtu.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by ace on 7/8/17.
 */
@Entity
@Table(name = "user_role")
public class RoleUserEntity implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private UserEntity userEntity;

    @Id
    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleEntity roleEntity;

    public RoleUserEntity() {
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public RoleEntity getRoleEntity() {
        return roleEntity;
    }

    public void setRoleEntity(RoleEntity roleEntity) {
        this.roleEntity = roleEntity;
    }
}
