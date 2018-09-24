package warehouse.dao;

import org.springframework.data.repository.CrudRepository;
import warehouse.entity.ItemsDelivery;

public interface ItemDeliveryDAO extends CrudRepository<ItemsDelivery,Long> {
}
