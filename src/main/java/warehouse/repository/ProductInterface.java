package warehouse.repository;

import warehouse.entity.Product;


import java.util.List;

public interface ProductInterface {

    void save(Product product);
    List<Product> findall();

}
