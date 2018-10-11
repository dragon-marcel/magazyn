package warehouse.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import warehouse.dao.DeliveryDAO;
import warehouse.entity.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository(value = "DocumentWarehouseMain")
public class DeliveryMainRepository implements DeliveryImpl {
    @Autowired
    private DeliveryDAO deliveryDAO;
    @Autowired
    private StateProductsRepository stateProductsRepository;
    @Qualifier("DocumentWarehouseShop")
    @Autowired
    @Lazy
    DeliveryImpl deliveryImpl;

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional()
    public void save(Delivery delivery){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nameUser = authentication.getName();
        if (delivery.getDocument().getId() == 2 || delivery.getDocument().getId() == 4) {
            throw new NullPointerException("Nie możesz wydać dowaru Klientówi z magazynu głównego.");
        }
        delivery.setNameUser(nameUser);
            em.persist(delivery);
    }

    @Override
    @Transactional
    public void delete(Delivery delivery) {

        List<ItemsDelivery> itemsDeliveries = delivery.getItemdeliveries();
        Delivery secondDelivery = delivery.getDeliverySecond();

        if (delivery.isConfirm()){

            //DOCUMENT "WYDANIE MAGAZYNOWE"
            if(delivery.getDocument().getId() == 1) {
            for (int a = 0; a < itemsDeliveries.size(); a++) {
                stateProductsRepository.addtoStateProducts(itemsDeliveries.get(a).getProduct(),
                        itemsDeliveries.get(a).getQuantity(), 1L);
                stateProductsRepository.checkStateProduct(itemsDeliveries.get(a).getProduct(),
                        itemsDeliveries.get(a).getQuantity(), 2L);
                stateProductsRepository.subtractFromStateProducts(itemsDeliveries.get(a).getProduct(),
                        itemsDeliveries.get(a).getQuantity(),2L);
            }
                em.remove(secondDelivery);
                em.remove(delivery);

            // DOCUMENT "PRZYJĘCIA MAGAZYNOWE"
            }else if (delivery.getDocument().getId() == 2){
                for (int a = 0; a < itemsDeliveries.size(); a++) {
                    stateProductsRepository.addtoStateProducts(itemsDeliveries.get(a).getProduct(),
                            itemsDeliveries.get(a).getQuantity(),2L);
                    stateProductsRepository.checkStateProduct(itemsDeliveries.get(a).getProduct(),
                            itemsDeliveries.get(a).getQuantity(), 1L);
                    stateProductsRepository.subtractFromStateProducts(itemsDeliveries.get(a).getProduct(),
                            itemsDeliveries.get(a).getQuantity(), 1L);
                }
                em.remove(secondDelivery);
                em.remove(delivery);

            //DOCUMENT "PRZYJĘCIE TOWARU"
            }else if (delivery.getDocument().getId() == 3 ){
                for (int a = 0; a < itemsDeliveries.size(); a++) {
                    stateProductsRepository.checkStateProduct(itemsDeliveries.get(a).getProduct(),
                            itemsDeliveries.get(a).getQuantity(), 1L);
                    stateProductsRepository.subtractFromStateProducts(itemsDeliveries.get(a).getProduct(),
                            itemsDeliveries.get(a).getQuantity(), 1L);
                }    em.remove(delivery);
            }
         //DOCUMENT NOT CONFIRM
        }else {
                em.remove(delivery);
            }
    }
    @Override
    public Delivery findById(Long id) {
        return deliveryDAO.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void saveItem(Delivery delivery) {
        List<ItemsDelivery>itemsDeliveries =delivery.getItemdeliveries();
        if(delivery.getDocument().getId() == 1 || delivery.getDocument().getId() == 4){
            for (int a = 0; a < itemsDeliveries.size(); a++) {
                stateProductsRepository.checkStateProduct(itemsDeliveries.get(a).getProduct(),
                        itemsDeliveries.get(a).getQuantity(), 1L);
                em.merge(delivery);
            }
        }
        }
    @Override
    @Transactional
    public void submit(Delivery delivery){
    List<ItemsDelivery>itemsDeliveries =delivery.getItemdeliveries();
        if (delivery.getDocument().getId() == 3 || delivery.getDocument().getId() ==2){
            for(int a = 0 ;a< itemsDeliveries.size();a++){
                stateProductsRepository.addtoStateProducts(itemsDeliveries.get(a).getProduct(),
                        itemsDeliveries.get(a).getQuantity(),1L);
                em.merge(delivery);
            }}
        else {
            for(int a = 0 ; a < itemsDeliveries.size();a++){
                stateProductsRepository.subtractFromStateProducts(itemsDeliveries.get(a).getProduct(),
                        itemsDeliveries.get(a).getQuantity(),1L);
                em.merge(delivery);
            }

            String nameUser = delivery.getNameUser();
            if(delivery.getDocument().getId() == 1){
                Delivery deliverySecond = new Delivery();
                Warehouse warehouse  = em.find(Warehouse.class,2L);
                Document document = em.find(Document.class,2L);
                List<ItemsDelivery>seconditemsDeliveries = new ArrayList<>();
                seconditemsDeliveries.addAll(itemsDeliveries);

                for (ItemsDelivery items:seconditemsDeliveries){
                    items.setId(null);
                    items.setDelivery(deliverySecond);
                }
                deliverySecond.setWarehouse(warehouse);
                deliverySecond.setNameUser(nameUser);
                deliverySecond.setDocument(document);
                deliverySecond.setDate(delivery.getDate());
                deliverySecond.setDescription(delivery.getDescription());
                deliverySecond.setDelivery(delivery);
                delivery.setDeliverySecond(deliverySecond);
                deliverySecond.setConfirm(true);

                for(int a = 0 ;a< itemsDeliveries.size();a++){
                    stateProductsRepository.addtoStateProducts(itemsDeliveries.get(a).getProduct(),
                            seconditemsDeliveries.get(a).getQuantity(),2L);
                }
                em.merge(deliverySecond);}
        }

    }
}