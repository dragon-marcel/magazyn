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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;
@Repository
public class DeliveryMainMainRepository implements DeliveryMainInterface {
    @Autowired
    private DeliveryDAO deliveryDAO;

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional()
    public void save(Delivery delivery) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nameUser = authentication.getName();
        if(delivery.getDocument().getId() == 2 || delivery.getDocument().getId() == 4){
            throw new PersistenceException("Nie możesz wydać dowaru Klientówi z magazynu głównego.");
        }
        delivery.setNameUser(nameUser);
        em.persist(delivery);

        if(delivery.getDocument().getId() == 1){
         Delivery delivery1 = new Delivery();
         Warehouse warehouse  = (Warehouse)em.createQuery("select w from Warehouse w where id ='2'").getSingleResult();
         Document document = (Document)em.createQuery("select d from Document d where id = 2").getSingleResult();
         delivery1.setWarehouse(warehouse);
         delivery1.setNameUser(nameUser);
         delivery1.setDocument(document);
         delivery1.setDate(delivery.getDate());
         delivery1.setDescription(delivery.getDescription());
         delivery1.setDelivery(delivery);
         delivery.setDelivery(delivery1);
         em.persist(delivery1);}}
    @Override
    public List<Delivery> findAll() {
       List<Delivery>deliveries =(List<Delivery>)deliveryDAO.findAll();
        return deliveries;
    }

    @Override
    @Transactional
    public void delete(Delivery delivery) {
        Delivery deliveryFist = delivery;
        Delivery deliverySecond = delivery.getDelivery();
        if (deliverySecond.getId() == null){
            em.remove(deliveryFist);

        }else{
            em.remove(deliveryFist);
            em.remove(deliverySecond);
        }}

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
