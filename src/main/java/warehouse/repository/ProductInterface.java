package warehouse.repository;

import warehouse.entity.Product;


import java.util.List;

public interface ProductInterface {

    void save(Product product);
    List<Product> findall();
    void delete(Product product);
    Product find(Long id);
    List<Product>productWarehouseMain();
    List<Product>findProductByName(String term);
}
