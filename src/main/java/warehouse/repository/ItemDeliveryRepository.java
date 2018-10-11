package warehouse.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import warehouse.entity.Delivery;
import warehouse.entity.ItemsDelivery;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class ItemDeliveryRepository implements ItemDeliveryIntefrace{
    @Autowired
    private StateProductsInterface stateProductsInterface;

    @PersistenceContext
    private EntityManager em;

    @Override
    public ItemsDelivery findOneById(Long id) {
        return em.find(ItemsDelivery.class,id);
    }

    @Override
    @Transactional
    public void delete(ItemsDelivery itemsDelivery) throws Exception {

        Delivery delivery = itemsDelivery.getDelivery();
        Long warehouseId = itemsDelivery.getDelivery().getWarehouse().getId();
        Long documentId = itemsDelivery.getDelivery().getDocument().getId();

        if (delivery.isConfirm()) {

            //DOCUMENT "KLIENT"
            if (documentId == 4){

           stateProductsInterface.addtoStateProducts(itemsDelivery.getProduct(),
                    itemsDelivery.getQuantity(), warehouseId);
            em.remove(itemsDelivery);

            //DOCUMENT "PRZYJĘCIE TOWARU"
            }else if (documentId == 3){
                stateProductsInterface.checkStateProduct(itemsDelivery.getProduct(),itemsDelivery.getQuantity(),warehouseId);
                stateProductsInterface.subtractFromStateProducts(itemsDelivery.getProduct(),itemsDelivery.getQuantity(),warehouseId);
            em.remove(itemsDelivery);
            }
        // IS NOT CONFIRM
        } else {
            em.remove(itemsDelivery);
        }
    }

    @Override
    @Transactional
    public void saveItem(ItemsDelivery itemsDelivery) throws Exception {

        Delivery delivery = itemsDelivery.getDelivery();
        Long warehouseId = itemsDelivery.getDelivery().getWarehouse().getId();
        Long documentId = itemsDelivery.getDelivery().getDocument().getId();

        if (delivery.isConfirm()) {

            //DOCUMENT "KLIENT"
            if (documentId == 4) {
                stateProductsInterface.checkStateProduct(itemsDelivery.getProduct(), itemsDelivery.getQuantity(), warehouseId);
                stateProductsInterface.subtractFromStateProducts(itemsDelivery.getProduct(),
                        itemsDelivery.getQuantity(), warehouseId);
                em.persist(itemsDelivery);

              //DOCUMENT "PRZYJĘCIE TOWARU"
            } else if (documentId == 3) {
                stateProductsInterface.addtoStateProducts(itemsDelivery.getProduct(),
                        itemsDelivery.getQuantity(), warehouseId);
                em.persist(itemsDelivery);
            }

        // IS NOT CONFIRM
        } else {
            stateProductsInterface.checkStateProduct(itemsDelivery.getProduct(), itemsDelivery.getQuantity(), warehouseId);
            em.persist(itemsDelivery);
        }
    }}
