package org.sjtu.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by ace on 6/13/17.
 */
@Entity
@Table(name = "order_apply")
public class OrderApplyEntity implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference(value = "orderItems")
    private OrderEntity orderEntity;

    @Id
    @ManyToOne
    @JoinColumn(name = "book_id")
    private BookEntity bookEntity;

    @Column(name = "book_num")
    private int bookNumber;

    public OrderApplyEntity() {}

    public OrderEntity getOrderEntity() {
        return orderEntity;
    }

    public void setOrderEntity(OrderEntity orderEntity) {
        this.orderEntity = orderEntity;
    }

    public BookEntity getBookEntity() {
        return bookEntity;
    }

    public void setBookEntity(BookEntity bookEntity) {
        this.bookEntity = bookEntity;
    }

    public int getBookNumber() {
        return bookNumber;
    }

    public void setBookNumber(int bookNumber) {
        this.bookNumber = bookNumber;
    }


}
