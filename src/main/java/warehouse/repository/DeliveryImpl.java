package warehouse.repository;

import warehouse.entity.Delivery;

public interface DeliveryImpl {
    void save (Delivery delivery) throws Exception;
    void delete(Delivery delivery);
    Delivery findById(Long id);
    void saveItem(Delivery delivery);
    void submit(Delivery delivery);

}
