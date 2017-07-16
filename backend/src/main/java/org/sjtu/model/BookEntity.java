package org.sjtu.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Created by ace on 6/11/17.
 */
@Entity
@Table(name = "book")
public class BookEntity {
    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer bookId;

    @Column(name = "book_name", length = 128)
    private String bookName;

    @Column(name = "book_desc", length = 512)
    private String bookDesc;

    @Column(name = "book_price")
    private Integer bookPrice;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity bookCategory;

    @OneToMany(mappedBy = "bookEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<OrderApplyEntity> orderApplyEntities;


    public BookEntity() {
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookDesc() {
        return bookDesc;
    }

    public void setBookDesc(String bookDesc) {
        this.bookDesc = bookDesc;
    }

    public Integer getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(Integer bookPrice) {
        this.bookPrice = bookPrice;
    }

    public CategoryEntity getBookCategory() {
        return bookCategory;
    }

    public void setBookCategory(CategoryEntity bookCategory) {
        this.bookCategory = bookCategory;
    }

    public List<OrderApplyEntity> getOrderApplyEntities() {
        return orderApplyEntities;
    }

    public void setOrderApplyEntities(List<OrderApplyEntity> orderApplyEntities) {
        this.orderApplyEntities = orderApplyEntities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookEntity)) return false;

        BookEntity book = (BookEntity) o;

        if (!bookId.equals(book.bookId)) return false;
        if (bookName != null ? !bookName.equals(book.bookName) : book.bookName != null) return false;
        if (bookDesc != null ? !bookDesc.equals(book.bookDesc) : book.bookDesc != null) return false;
        if (bookPrice != null ? !bookPrice.equals(book.bookPrice) : book.bookPrice != null) return false;
        return bookCategory != null ? bookCategory.equals(book.bookCategory) : book.bookCategory == null;
    }

    @Override
    public int hashCode() {
        int result = bookId.hashCode();
        result = 31 * result + (bookName != null ? bookName.hashCode() : 0);
        result = 31 * result + (bookDesc != null ? bookDesc.hashCode() : 0);
        result = 31 * result + (bookPrice != null ? bookPrice.hashCode() : 0);
        result = 31 * result + (bookCategory != null ? bookCategory.hashCode() : 0);
        return result;
    }

}
