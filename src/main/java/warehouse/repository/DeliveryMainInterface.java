package warehouse.repository;

import org.springframework.stereotype.Repository;
import warehouse.entity.Delivery;

import java.util.List;
public interface DeliveryMainInterface {
    void save (Delivery delivery) throws Exception;
    List<Delivery> findAll();
    void delete(Delivery delivery);
    Delivery findById(Long id);
    void merge (Delivery delivery);

}
