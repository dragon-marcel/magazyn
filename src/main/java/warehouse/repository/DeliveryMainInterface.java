package warehouse.repository;

import warehouse.entity.Delivery;

public interface DeliveryMainInterface {
    void save (Delivery delivery) throws Exception;
    void delete(Delivery delivery);
    Delivery findById(Long id);
    void merge (Delivery delivery);

}
