package org.sjtu.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

/**
 * Created by ace on 6/11/17.
 */
@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "username", nullable = false, length = 16, unique = true)
    private String username;

    @Column(name = "email", length = 255, unique = true, nullable = false)
    private String email;

    @Column(name = "password",nullable = false, length = 32)
    private String password;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "create_time",insertable = false, updatable = false, nullable = false)
    private Timestamp createTime;

    @Column(name = "update_time", nullable = false, updatable = false, insertable = false)
    private Timestamp updateTime;

    @OneToMany(mappedBy = "orderUser", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<OrderEntity> orderEntities;

    @OneToMany(mappedBy = "addrUser", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "addrUser")
    private List<AddressEntity> addressEntities;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private List<RoleUserEntity> roleUserEntities;


    public UserEntity() {}
    public UserEntity(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<OrderEntity> getOrderEntities() {
        return orderEntities;
    }

    public void setOrderEntities(List<OrderEntity> orderEntities) {
        this.orderEntities = orderEntities;
    }

    public List<AddressEntity> getAddressEntities() {
        return addressEntities;
    }

    public void setAddressEntities(List<AddressEntity> addressEntities) {
        this.addressEntities = addressEntities;
    }

    public List<RoleUserEntity> getRoleUserEntities() {
        return roleUserEntities;
    }

    public void setRoleUserEntities(List<RoleUserEntity> roleUserEntities) {
        this.roleUserEntities = roleUserEntities;
    }
}
