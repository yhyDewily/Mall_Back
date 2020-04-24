package com.demo.service;

import com.demo.common.ServerResponse;
import com.demo.dataobject.Category;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


public interface CategoryService {

    ServerResponse addCategory(String categoryName, Integer parentId);

    int updateAll(Category category);

    ServerResponse updateCategoryName(Integer categoryId, String categoryName);

    ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId);

    ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId);

    Set<Category> findChildCategory(Set<Category> categorySet, Integer categoryId);
}
