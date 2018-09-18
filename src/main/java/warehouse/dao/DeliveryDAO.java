package warehouse.dao;

import org.springframework.data.repository.CrudRepository;
import warehouse.entity.Delivery;

public interface DeliveryDAO extends CrudRepository<Delivery,Long>{
}
