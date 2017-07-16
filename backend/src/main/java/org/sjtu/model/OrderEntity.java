package org.sjtu.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by ace on 6/13/17.
 */
@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "order_time", nullable = false, updatable = false, insertable = false)
    private Timestamp orderTime;

    @Column(name = "order_price")
    private Integer orderPrice;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity orderUser;

    @OneToMany(mappedBy = "orderEntity", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "orderItems")
    private List<OrderApplyEntity> orderApplyEntities;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Timestamp getOrderTime() {
        return orderTime;
    }

    public Integer getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(int price) {
        this.orderPrice = price;
    }

    public UserEntity getOrderUser() {
        return orderUser;
    }

    public void setOrderUser(UserEntity user) {
        this.orderUser = user;
    }

    public List<OrderApplyEntity> getOrderApplyEntities() {
        return orderApplyEntities;
    }

    public void setOrderApplyEntities(List<OrderApplyEntity> orderApplyEntities) {
        this.orderApplyEntities = orderApplyEntities;
    }
}
