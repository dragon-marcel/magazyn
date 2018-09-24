package warehouse.repository;

import org.springframework.data.repository.CrudRepository;
import warehouse.dao.StateProductsDAO;
import warehouse.entity.Product;
import warehouse.entity.StateProducts;

import java.util.List;

public interface StateProductsInterface {
     List<StateProducts> findAllStateProductsbyWarehouse(Long id);
     public void addtoStateProducts(Product product, Long quantity, Long warehouseID);
     public void subtractFromStateProducts(Product product, Long quantity, Long warehouseID) throws Exception;
     }
