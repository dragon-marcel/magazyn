package warehouse.dao;

import org.springframework.data.repository.CrudRepository;
import warehouse.entity.Product;

public interface ProductDAO extends CrudRepository<Product,Long> {
}
