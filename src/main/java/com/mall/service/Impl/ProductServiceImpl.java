package com.mall.service.Impl;

import com.mall.common.Const;
import com.mall.common.GrandUtil;
import com.mall.common.ResponseCode;
import com.mall.common.ServerResponse;
import com.mall.dataobject.Category;
import com.mall.dataobject.Product;
import com.mall.esrepository.crud.ProductCrudRepo;
import com.mall.repository.CategoryRepository;
import com.mall.repository.GrandRepository;
import com.mall.repository.ProductRepository;
import com.mall.service.ProductService;
import com.mall.vo.ProductDetailVo;
import com.mall.vo.ProductVo;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.Arrays;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository repository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductCrudRepo crudRepo;

    @Autowired
    GrandRepository grandRepository;

    @Override
    public ServerResponse saveOrUpdateProduct(ProductVo productVo) {
        if(productVo != null) {
            Product product = new Product();
            if(productVo.getId()!=null) {
                product = repository.findByProductId(productVo.getId());
                //修改product的值
                product.setCategoryId(productVo.getCategoryId());
                product.setName(productVo.getName());
                product.setSubtitle(productVo.getSubtitle());
                product.setMainImage(productVo.getMainImage());

                StringBuilder params = new StringBuilder();
                List<String> paramList = productVo.getParams();
                for(int i=0;i<paramList.size();i++) {
                    if(productVo.getGrandId() == 2) {
                        if(i==0) params = new StringBuilder(paramList.get(i));
                        else params.append("#").append(paramList.get(i));
//                productVo.setParams(Arrays.asList(product.getParams().split("#")));
                    } else if (productVo.getGrandId() == 1) {
                        if(i==0) params = new StringBuilder(paramList.get(i));
                        else params.append(",").append(paramList.get(i));
//                productVo.setParams(Arrays.asList(product.getParams().split(",")));
                    } else{
                        if(i==0) params = new StringBuilder(paramList.get(i));
                        else params.append(", ").append(paramList.get(i));
//                productVo.setParams(Arrays.asList(product.getParams().split(", ")));
                    }
                }
                product.setParams(String.valueOf(params));

                StringBuilder images = new StringBuilder();
                List<String> imageList = productVo.getSubImages();
                for(int i=0;i<imageList.size();i++) {
                    if( i== 0) images = new StringBuilder(imageList.get(i));
                    else images.append(", ").append(imageList.get(i));
                }
                product.setSubImages(String.valueOf(images));
                product.setDetail(productVo.getDetail());
                product.setPrice(productVo.getPrice());
                product.setStock(productVo.getStock());
                product.setStatus(productVo.getStatus());
                product.setGrandId(productVo.getGrandId());
            } else {
                product = VoToProduct(productVo);
            }
            int rowCount = this.updateOrInsert(product);
            if(productVo.getId() != null){
                if(rowCount > 0){
                    return ServerResponse.createBySuccess("更新产品成功");
                }
                return ServerResponse.createBySuccess("更新产品失败");
            }else{
                if(rowCount > 0){
                    return ServerResponse.createBySuccess("新增产品成功");
                }
                return ServerResponse.createBySuccess("新增产品失败");
            }
        }
        return ServerResponse.createByErrorMessage("新增或更新产品参数不正确");
    }

    private int updateOrInsert(Product product){
        try {
            repository.save(product);
        } catch (Exception e){
            return 0;
        }
        return 1;
    }

    public Product getProductById(Integer productId) {
        return repository.findByProductId(productId);
    }


    @Override
    public ProductVo getProductInfo(Integer productId) {
        Product product = repository.findByProductId(productId);
        ProductVo productVo = new ProductVo();
        productVo.setName(product.getName());
        productVo.setId(product.getId());
        productVo.setCategoryId(product.getCategoryId());
        productVo.setMainImage(product.getMainImage());
        productVo.setSubtitle(product.getSubtitle());
        productVo.setDetail(product.getDetail());
        productVo.setGrandId(product.getGrandId());
        productVo.setPrice(product.getPrice());
        productVo.setStock(product.getStock());
        productVo.setStatus(product.getStatus());

        productVo.setSubImages(Arrays.asList(product.getSubImages().split(", ")));
        if(product.getGrandId() == 2) {
            productVo.setParams(Arrays.asList(product.getParams().split("#")));
        } else if (product.getGrandId() == 1) {
            productVo.setParams(Arrays.asList(product.getParams().split(",")));
        } else{
            productVo.setParams(Arrays.asList(product.getParams().split(", ")));
        }
        return productVo;
    }

    @Override
    public List<Product> getAll() {
        return repository.findAllProduct();
    }

    @Override
    public ServerResponse setSaleStatus(Integer productId, Integer status) {
        if(productId == null || status == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = repository.findByProductId(productId);
        product.setStatus(status);
        int rowCount = this.updateOrInsert(product);
        if(rowCount > 0){
            return ServerResponse.createBySuccess("修改产品销售状态成功");
        }
        return ServerResponse.createByErrorMessage("修改产品销售状态失败");
    }

    @Override
    public ServerResponse manageProductDetail(Integer productId) {
        if(productId == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = repository.findByProductId(productId);
        if(product == null){
            return ServerResponse.createByErrorMessage("产品已下架或者删除");
        }
        ProductDetailVo productDetailVo = assembleProductDetailVo(product);
        return ServerResponse.createBySuccess(productDetailVo);
    }

    private ProductDetailVo assembleProductDetailVo(Product product){
        ProductDetailVo productDetailVo = new ProductDetailVo();
        productDetailVo.setId(product.getId());
        productDetailVo.setSubtitle(product.getSubtitle());
        productDetailVo.setPrice(product.getPrice());
        productDetailVo.setMainImage(product.getMainImage());
        productDetailVo.setSubImages(product.getSubImages());
        productDetailVo.setCategoryId(product.getCategoryId());
        productDetailVo.setDetail(product.getDetail());
        productDetailVo.setName(product.getName());
        productDetailVo.setStatus(product.getStatus());
        productDetailVo.setStock(product.getStock());
        productDetailVo.setGrand(grandRepository.findNameById(product.getGrandId()));
        Category category = categoryRepository.findCategoryById(product.getCategoryId());
        if(category == null){
            productDetailVo.setParentCategoryId(0);//默认根节点
        }else{
            productDetailVo.setParentCategoryId(category.getParentId());
        }

        return productDetailVo;
    }

    @Override
    public ServerResponse<Page> getProductList(int currentPage, int pageSize){
        //startPage--start
        //填充自己的sql查询逻辑
        //pageHelper-收尾
        Pageable pageable = PageRequest.of(currentPage, pageSize);
        Page<Product> productPage = repository.findAll(pageable);
        return ServerResponse.createBySuccess(productPage);
    }

//    private ProductListVo assembleProductListVo(Product product){
//        ProductListVo productListVo = new ProductListVo();
//        productListVo.setId(product.getId());
//        productListVo.setName(product.getName());
//        productListVo.setCategoryId(product.getCategoryId());
//        productListVo.setMainImage(product.getMainImage());
//        productListVo.setPrice(product.getPrice());
//        productListVo.setSubtitle(product.getSubtitle());
//        productListVo.setStatus(product.getStatus());
//        return productListVo;
//    }

    @Override
    public ServerResponse searchProduct(String productName, int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage, pageSize);
        try {
            String searchStr = productName;
            QueryStringQueryBuilder builder = new QueryStringQueryBuilder(searchStr);
            Page<Product> search = crudRepo.search(builder, pageable);
            return ServerResponse.createBySuccess(search);
        } catch (Exception e) {
            System.out.println(e);
            return ServerResponse.createByErrorMessage("查找失败");
        }


    }

    @Override
    public ServerResponse<ProductDetailVo> getProductDetail(Integer productId) {
        if(productId == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = repository.findByProductId(productId);
        if(product == null){
            return ServerResponse.createByErrorMessage("产品已下架或者删除");
        }
        if(product.getStatus() != Const.ProductStatusEnum.ON_SALE.getCode()){
            return ServerResponse.createByErrorMessage("产品已下架或者删除");
        }
        ProductDetailVo productDetailVo = assembleProductDetailVo(product);
        return ServerResponse.createBySuccess(productDetailVo);
    }

    @Override
    public ServerResponse getProductByKeywordCategory(String keyword, int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum-1, pageSize);
        if(GrandUtil.getGrandList().contains(keyword)){  //品牌英文名查找
            int grandId = grandRepository.findIdByName(keyword);
            Page<Product> page = repository.findAllByGrandId(grandId, pageable);
            return ServerResponse.createBySuccess(page);
        } else if(GrandUtil.getGrandCList().contains(keyword)){ //品牌中文名查找
            for(GrandUtil grandUtil: GrandUtil.values()){
                if(grandUtil.getGrandCName().equals(keyword)){
                    int categoryId = grandUtil.getGrandId();
                    Page<Product> page = repository.findAllByGrandId(categoryId, pageable);
                    return ServerResponse.createBySuccess(page);
                }
            }
        } else if(keyword.equals("男子") || keyword.equals("男装")) {
            return ServerResponse.createBySuccess(repository.findProductBySex(100001, pageable));
        } else if(keyword.equals("女子") || keyword.equals("女装")) {
            return ServerResponse.createBySuccess(repository.findProductBySex(100002, pageable));
        } else {
            try {
                String searchStr = keyword;
                QueryStringQueryBuilder builder = new QueryStringQueryBuilder(searchStr);
                Page<Product> search = crudRepo.search(builder, pageable);
                return ServerResponse.createBySuccess(search);
            } catch (Exception e) {
                System.out.println(e);
                return ServerResponse.createByErrorMessage("查找失败");
            }
        }
        return ServerResponse.createByErrorMessage("查找失败");
    }

    public ServerResponse getProductBySex(int categoryId, int pageNum, int pageSize){
        Pageable pageable = PageRequest.of(pageNum-1, pageSize);
        return ServerResponse.createBySuccess(repository.findProductBySex(categoryId, pageable));
    }

    public ServerResponse<Page<Product>> getProductByGrand(String grandName, int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        if(GrandUtil.getGrandList().contains(grandName)){  //品牌英文名查找
            int grandId = grandRepository.findIdByName(grandName);
            Page<Product> page = repository.findAllByGrandId(grandId, pageable);
            return ServerResponse.createBySuccess(page);
        }
        return ServerResponse.createByErrorMessage("查找失败");
    }

    public ServerResponse getProductByGrandId(Integer grandId, int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        try {
            Page<Product> page = repository.findAllByGrandId(grandId, pageable);
            return ServerResponse.createBySuccess(page);
        } catch (Exception e) {
            return ServerResponse.createByErrorMessage("查找失败");
        }
    }

    @Override
    public ServerResponse getProductByCategory(Integer keyword, int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        try {
            Page<Product> page = repository.findByCategoryId(keyword, pageable);
            return ServerResponse.createBySuccess(page);
        } catch (Exception e) {
            return ServerResponse.createByErrorMessage("查找失败");
        }

    }

    public Product VoToProduct(ProductVo productVo) {
        Product product = new Product();
        product.setCategoryId(productVo.getCategoryId());
        product.setName(productVo.getName());
        product.setSubtitle(productVo.getSubtitle());
        product.setMainImage(productVo.getMainImage());

        StringBuilder params = new StringBuilder();
        List<String> paramList = productVo.getParams();
        for(int i=0;i<paramList.size();i++) {
            if(productVo.getGrandId() == 2) {
                if(i==0) params = new StringBuilder(paramList.get(i));
                else params.append("#").append(paramList.get(i));
//                productVo.setParams(Arrays.asList(product.getParams().split("#")));
            } else if (productVo.getGrandId() == 1) {
                if(i==0) params = new StringBuilder(paramList.get(i));
                else params.append(",").append(paramList.get(i));
//                productVo.setParams(Arrays.asList(product.getParams().split(",")));
            } else{
                if(i==0) params = new StringBuilder(paramList.get(i));
                else params.append(", ").append(paramList.get(i));
//                productVo.setParams(Arrays.asList(product.getParams().split(", ")));
            }
        }
        product.setParams(String.valueOf(params));

        StringBuilder images = new StringBuilder();
        List<String> imageList = productVo.getSubImages();
        for(int i=0;i<imageList.size();i++) {
            if( i== 0) images = new StringBuilder(imageList.get(i));
            else images.append(", ").append(imageList.get(i));
        }
        product.setSubImages(String.valueOf(images));
        product.setDetail(productVo.getDetail());
        product.setPrice(productVo.getPrice());
        product.setStock(productVo.getStock());
        product.setStatus(productVo.getStatus());
        product.setGrandId(productVo.getGrandId());
        return product;
    }


}
