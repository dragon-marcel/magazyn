package warehouse.repository;

import org.springframework.stereotype.Repository;
import warehouse.entity.Delivery;

import java.util.List;

public interface DeliveryShopInterface {
    void save (Delivery delivery) throws Exception;
    List<Delivery> findA();
    void delete(Delivery delivery);
    Delivery findById(Long id);
    void merge (Delivery delivery);
}
