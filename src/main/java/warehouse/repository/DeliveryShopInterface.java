package warehouse.repository;

import warehouse.entity.Delivery;

public interface DeliveryShopInterface {
    void save (Delivery delivery) throws Exception;
    void delete(Delivery delivery);
    Delivery findById(Long id);
    void saveItem(Delivery delivery);
}
