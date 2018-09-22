package warehouse.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import warehouse.entity.Product;

import java.util.List;

public interface ProductDAO extends CrudRepository<Product,Long> {

    @Query(value = "select p from Product p where p.name like %?1%")
    List<Product>findProductByName(String term);
}
