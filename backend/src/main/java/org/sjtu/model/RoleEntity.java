package org.sjtu.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;
import javax.websocket.ClientEndpoint;
import java.util.List;

/**
 * Created by ace on 7/8/17.
 */
@Entity
@Table(name = "role")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "role_name", length = 32)
    private String roleName;

    @Column(name = "role_desc", length = 255)
    private String roleDesc;

    @OneToMany(mappedBy = "roleEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<RoleUserEntity> roleUserEntities;

    public RoleEntity() {
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public List<RoleUserEntity> getRoleUserEntities() {
        return roleUserEntities;
    }

    public void setRoleUserEntities(List<RoleUserEntity> roleUserEntities) {
        this.roleUserEntities = roleUserEntities;
    }
}
