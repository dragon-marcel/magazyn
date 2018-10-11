package warehouse.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import warehouse.dao.DeliveryDAO;
import warehouse.entity.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Repository(value = "DocumentWarehouseShop")
public class DeliveryShopRepository implements DeliveryImpl {
    @Autowired
    private DeliveryDAO deliveryDAO;
    @Autowired
    private StateProductsRepository stateProductsRepository;
    @Qualifier("DocumentWarehouseMain")
    @Autowired
    DeliveryImpl deliveryImpl;

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void save(Delivery document) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nameUser = authentication.getName();
        document.setNameUser(nameUser);

        em.persist(document);

        if (document.getDocument().getId() == 3 ||document.getDocument().getId() == 2) {
            throw new NullPointerException("Nie możesz Przyjąć dostawy.");
        }
    }
    @Override
    @Transactional
    public void delete(Delivery delivery) {

        List<ItemsDelivery> itemsDeliveries = delivery.getItemdeliveries();
        Delivery secondDelivery = delivery.getDeliverySecond();

        if (delivery.isConfirm()){

            //DOCUMENT "WYDANIE MAGAZYNOWE"
            if (delivery.getDocument().getId() == 1) {
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

            // DOCUMENT "PRZYJĘCIA MAGAZYNOWE"
            }else if (delivery.getDocument().getId() == 2){
                for (int a = 0; a < itemsDeliveries.size(); a++) {
                    stateProductsRepository.addtoStateProducts(itemsDeliveries.get(a).getProduct(),
                        itemsDeliveries.get(a).getQuantity(),1L);
                stateProductsRepository.checkStateProduct(itemsDeliveries.get(a).getProduct(),
                        itemsDeliveries.get(a).getQuantity(), 2L);
                stateProductsRepository.subtractFromStateProducts(itemsDeliveries.get(a).getProduct(),
                        itemsDeliveries.get(a).getQuantity(), 2L);
            }
            em.remove(secondDelivery);
            em.remove(delivery);
            }

            //DOCUMENT "KLIENT"
            else if(delivery.getDocument().getId() == 4 ){
                for (int a = 0; a < itemsDeliveries.size(); a++) {
                    stateProductsRepository.addtoStateProducts(itemsDeliveries.get(a).getProduct(),
                            itemsDeliveries.get(a).getQuantity(),2L);
                }
             em.remove(delivery);
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
        List<ItemsDelivery> itemsDeliveries = delivery.getItemdeliveries();

        if(delivery.getDocument().getId() == 3 || delivery.getDocument().getId() == 4){
            for (int a = 0; a < itemsDeliveries.size(); a++) {
                stateProductsRepository.checkStateProduct(itemsDeliveries.get(a).getProduct(),
                        itemsDeliveries.get(a).getQuantity(), 2L);
            }em.merge(delivery);
        }
       }
    @Override
    @Transactional
    public void submit(Delivery delivery){
        List<ItemsDelivery>itemsDeliveries =delivery.getItemdeliveries();
        if (delivery.getDocument().getId() == 3 || delivery.getDocument().getId() ==2){
            for(int a = 0 ;a< itemsDeliveries.size();a++){
                stateProductsRepository.addtoStateProducts(itemsDeliveries.get(a).getProduct(),
                        itemsDeliveries.get(a).getQuantity(),2L);
                em.merge(delivery);
            }}
        else {
            for(int a = 0 ; a < itemsDeliveries.size();a++){
                stateProductsRepository.subtractFromStateProducts(itemsDeliveries.get(a).getProduct(),
                        itemsDeliveries.get(a).getQuantity(),2L);
                em.merge(delivery);
            }

            if(delivery.getDocument().getId() == 1){

                Delivery deliverySecond = new Delivery();
                String nameUser = delivery.getNameUser();
                Warehouse warehouse  = em.find(Warehouse.class,1L);
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
                deliverySecond.setDeliverySecond(delivery);
                delivery.setDeliverySecond(deliverySecond);
                deliverySecond.setConfirm(true);

                for(int a = 0 ;a< itemsDeliveries.size();a++){
                    stateProductsRepository.addtoStateProducts(itemsDeliveries.get(a).getProduct(),
                            seconditemsDeliveries.get(a).getQuantity(),1L);
                }
                em.merge(deliverySecond);}
 }

    }}
