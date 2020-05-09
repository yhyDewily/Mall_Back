package com.mall.repository;

import com.mall.dataobject.Product;
import com.mall.esrepository.ProductRepo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Random;

@SpringBootTest
@RunWith(SpringRunner.class)
class ProductRepositoryTest {

    @Autowired
    ProductRepository repository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductRepo productRepo;

    @Test
    void testPage() {
        Pageable pageable = PageRequest.of(0,3);
        Page<Product> page = repository.findAll(pageable);

//        Page<Product> data = repository.findAllProduct(pageable);
//        System.out.println("总条数" + data.getTotalElements());
//        System.out.println("总页数" + data.getTotalPages());
        for (Product product : page) {
            System.out.println(product);
        }
    }

    @Test
    void testFind(){
        List<Product> products = repository.findAll();
        for (Product product : products) {
            if(product.getId()>=184){
                product.setCategoryId(100013);
                repository.save(product);
            }
        }
    }

    @Test
    void testSold() {
        List<Product> products = repository.findAll();
        Random random = new Random();
        for(Product product : products) {
            int ran = random.nextInt(100);
            product.setSold(ran);
            repository.save(product);
            productRepo.save(product);
        }
    }

//    @Test
//    void testFindAll(){
//        Pageable pageable = PageRequest.of(0, 15);
//        Page<Product> products = repository.findAll(pageable);
//        List<Product> productList = products.getContent();
//        List<ProductAdminVo> productAdminVos = new ArrayList<>();
//        for(Product product : productList) {
//            ProductAdminVo productAdminVo = new ProductAdminVo();
//            productAdminVo.setId(product.getId());
//            productAdminVo.setName(product.getName());
//            productAdminVo.setSubtitle(product.getSubtitle());
//            productAdminVo.setCategory(categoryRepository.findNameById(product.getCategoryId()));
//            productAdminVo.setGrand(product.getSubtitle());
//            productAdminVo.setPrice(product.getPrice());
//            productAdminVo.setStock(product.getStock());
//            productAdminVo.setStatus(product.getStatus());
//            productAdminVos.add(productAdminVo);
//        }
//        Page<ProductAdminVo> page = new PageImpl<ProductAdminVo>(productAdminVos);
//    }
}