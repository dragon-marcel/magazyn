package warehouse.repository;

import warehouse.entity.Delivery;

import java.util.List;

public interface DeliveryInterface {
    void save (Delivery delivery);
    List<Delivery> findAll();
    void delete(Delivery delivery);
    Delivery findById(Long id);

}
