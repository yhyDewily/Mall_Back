package com.mall.repository;

import com.mall.dataobject.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {


    @Query(value = "select * from mall.category where id=?1 ", nativeQuery = true)
    Category selectById(Integer categoryId);

    @Query(value = "select * from mall.category where parent_id=?1", nativeQuery = true)
    List<Category> selectCategoryChildrenByParentId(Integer categoryId);
}
