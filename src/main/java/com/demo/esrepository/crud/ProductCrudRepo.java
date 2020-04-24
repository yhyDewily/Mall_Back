package com.demo.esrepository.crud;

import com.demo.dataobject.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCrudRepo extends ElasticsearchRepository<Product, Integer> {

    Page<Product> findByNameContaining(String words, Pageable pageable);
}
