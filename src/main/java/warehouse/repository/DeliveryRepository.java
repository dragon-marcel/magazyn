package warehouse.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import warehouse.dao.DeliveryDAO;
import warehouse.entity.Delivery;

import java.util.List;
@Repository
public class DeliveryRepository implements DeliveryInterface {
    @Autowired
    private DeliveryDAO deliveryDAO;
    @Override
    public void save(Delivery delivery) {
        deliveryDAO.save(delivery);
    }

    @Override
    public List<Delivery> findAll() {
       List<Delivery>deliveries =(List<Delivery>)deliveryDAO.findAll();
        return deliveries;
    }

    @Override
    public void delete(Delivery delivery) {
        deliveryDAO.delete(delivery);
    }

    @Override
    public Delivery findById(Long id) {
        return deliveryDAO.findById(id).orElse(null);
    }
}
