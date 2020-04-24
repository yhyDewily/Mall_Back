package com.demo.esrepository;

import com.demo.dataobject.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepo extends ElasticsearchCrudRepository<Product, Integer> {

    Optional<Product> findById(Integer id);

    List<Product> findByNameContaining(String word_1);

}
