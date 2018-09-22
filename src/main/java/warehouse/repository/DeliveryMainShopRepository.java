package warehouse.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import warehouse.dao.DeliveryDAO;
import warehouse.entity.Delivery;
import warehouse.entity.Document;
import warehouse.entity.ItemsDelivery;
import warehouse.entity.Warehouse;

import javax.persistence.*;
import java.util.List;
@Repository
public class DeliveryMainShopRepository implements DeliveryShopInterface {
    @Autowired
    private DeliveryDAO deliveryDAO;
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void save(Delivery delivery) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nameUser = authentication.getName();
        delivery.setNameUser(nameUser);
        em.persist(delivery);

        if (delivery.getDocument().getId() == 3 ||delivery.getDocument().getId() == 2) {
            throw new TransactionRequiredException("Nie możesz Przyjąć dostawy.");
        }

        if(delivery.getDocument().getId() == 1){
            Delivery delivery1 = new Delivery();
            Warehouse warehouse  = em.find(Warehouse.class,1L);
            Document document = em.find(Document.class,2L);
            delivery1.setWarehouse(warehouse);
            delivery1.setNameUser(nameUser);
            delivery1.setDocument(document);
            delivery1.setDate(delivery.getDate());
            delivery1.setDescription(delivery.getDescription());
            delivery1.setDelivery(delivery);
            em.persist(delivery1);
    } }

    @Override
    public List<Delivery> findA() {
        return null;
    }

    @Override
    @Transactional
    public void delete(Delivery delivery) {
em.remove(delivery);
//        Delivery deliveryFist = delivery;
//        Delivery deliverySecond = delivery.getDelivery();
//        if (deliverySecond.getId() == null){
//            em.remove(deliveryFist);
//
//        }else{
//            em.remove(deliveryFist);
//            em.remove(deliverySecond);
//        }
    }
    @Override
    public Delivery findById(Long id) {

        return deliveryDAO.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void merge(Delivery delivery) {
       List<ItemsDelivery>deliveries = delivery.getItemdeliveries();
       Delivery deliverySecond = delivery.getDelivery();
       deliverySecond.setItemdeliveries(deliveries);
        em.merge(delivery);
        em.merge(deliveries);
    }
}
