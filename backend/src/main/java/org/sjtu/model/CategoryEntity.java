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
@Table(name = "category")
public class CategoryEntity {
    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer categoryId;

    @Column(name = "category_name", length = 32)
    private String categoryName;

    @Column(name = "category_desc", length = 255)
    private String categoryDesc;

    @OneToMany(mappedBy = "bookCategory", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<BookEntity> bookSet;

    public CategoryEntity() {
    }

    public CategoryEntity(String name) {
        this.categoryName = name;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDesc() {
        return categoryDesc;
    }

    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
    }

    public List<BookEntity> getBookSet() {
        return bookSet;
    }

    public void setBookSet(List<BookEntity> bookSet) {
        this.bookSet = bookSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoryEntity)) return false;

        CategoryEntity category = (CategoryEntity) o;

        if (categoryId != category.categoryId) return false;
        if (categoryName != null ? !categoryName.equals(category.categoryName) : category.categoryName != null)
            return false;
        if (categoryDesc != null ? !categoryDesc.equals(category.categoryDesc) : category.categoryDesc != null)
            return false;
        return bookSet != null ? bookSet.equals(category.bookSet) : category.bookSet == null;
    }
}
