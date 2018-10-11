package warehouse.repository;

import warehouse.entity.ItemsDelivery;

public interface ItemDeliveryIntefrace {
    ItemsDelivery findOneById(Long id);
    void delete(ItemsDelivery itemsDelivery) throws Exception;
    void saveItem(ItemsDelivery itemsDelivery) throws Exception;
}

