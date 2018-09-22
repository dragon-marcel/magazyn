package warehouse.repository;

import org.springframework.data.repository.CrudRepository;
import warehouse.dao.StateProductsDAO;
import warehouse.entity.StateProducts;

import java.util.List;

public interface StateProductsInterface {
     List<StateProducts> findAllStateProductsbyWarehouse(Long id);
}
