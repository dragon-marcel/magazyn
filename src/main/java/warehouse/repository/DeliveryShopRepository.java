package warehouse.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import warehouse.dao.DeliveryDAO;
import warehouse.entity.*;

import javax.persistence.*;
import java.util.List;
@Repository
public class DeliveryShopRepository implements DeliveryShopInterface {
    @Autowired
    private DeliveryDAO deliveryDAO;
    @Autowired
    private StateProductsRepository stateProductsRepository;
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
            delivery.setDelivery(delivery1);

            em.persist(delivery1);
    } }
    @Override
    @Transactional
    public void delete(Delivery document) {
        List<ItemsDelivery> itemsDeliveries = document.getItemdeliveries();

        if(document.getDocument().getId() == 3 || document.getDocument().getId() == 2){
            for (int a = 0; a < itemsDeliveries.size(); a++) {
                stateProductsRepository.subtractFromStateProducts(itemsDeliveries.get(a).getProduct(),
                        itemsDeliveries.get(a).getQuantity(), 2L);
            }
                            em.remove(document);
        }
        else{
            for (int a = 0; a < itemsDeliveries.size(); a++) {
                stateProductsRepository.addtoStateProducts(itemsDeliveries.get(a).getProduct(),
                        itemsDeliveries.get(a).getQuantity(), 2L);
            }
            em.remove(document);

        }
    }
    @Override
    public Delivery findById(Long id) {

        return deliveryDAO.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void merge(Delivery delivery) {
        List<ItemsDelivery> itemsDeliveries = delivery.getItemdeliveries();
        if(delivery.getDocument().getId() == 3 || delivery.getDocument().getId() == 2){
            for (int a = 0; a < itemsDeliveries.size(); a++) {
                stateProductsRepository.addtoStateProducts(itemsDeliveries.get(a).getProduct(),
                        itemsDeliveries.get(a).getQuantity(), 2L);
                

            }
           em.merge(delivery);
        }
        else{
            for (int a = 0; a < itemsDeliveries.size(); a++) {
                stateProductsRepository.subtractFromStateProducts(itemsDeliveries.get(a).getProduct(),
                        itemsDeliveries.get(a).getQuantity(), 2L);

            }
            em.merge(delivery);
        }
    }}
