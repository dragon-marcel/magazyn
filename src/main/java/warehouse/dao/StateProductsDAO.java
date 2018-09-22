package warehouse.dao;

import org.springframework.data.repository.CrudRepository;
import warehouse.entity.StateProducts;

public interface StateProductsDAO extends CrudRepository<StateProducts,Long> {

}
