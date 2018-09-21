package warehouse.dao;

import org.springframework.data.repository.CrudRepository;
import warehouse.entity.Warehouse;

public interface WarehouseDAO extends CrudRepository<Warehouse,Long> {
}
