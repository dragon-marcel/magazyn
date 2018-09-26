package warehouse.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import warehouse.dao.DeliveryDAO;
import warehouse.entity.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;
@Repository
public class DeliveryMainRepository implements DeliveryMainInterface {
    @Autowired
    private DeliveryDAO deliveryDAO;
    @Autowired
    private StateProductsRepository stateProductsRepository;

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
         Warehouse warehouse  = em.find(Warehouse.class,2L);
         Document document = em.find(Document.class,2L);
         delivery1.setWarehouse(warehouse);
         delivery1.setNameUser(nameUser);
         delivery1.setDocument(document);
         delivery1.setDate(delivery.getDate());
         delivery1.setDescription(delivery.getDescription());
         delivery1.setDelivery(delivery);
         delivery.setDelivery(delivery1);
         em.persist(delivery1);}}

    @Override
    @Transactional
    public void delete(Delivery document) {
        List<ItemsDelivery> itemsDeliveries = document.getItemdeliveries();

        if(document.getDocument().getId() == 3 || document.getDocument().getId() == 2){
            for (int a = 0; a < itemsDeliveries.size(); a++) {
                stateProductsRepository.subtractFromStateProducts(itemsDeliveries.get(a).getProduct(),
                        itemsDeliveries.get(a).getQuantity(), 1L);
                em.remove(document);

            }
        }
        else{
            for (int a = 0; a < itemsDeliveries.size(); a++) {
                stateProductsRepository.addtoStateProducts(itemsDeliveries.get(a).getProduct(),
                        itemsDeliveries.get(a).getQuantity(), 1L);
                em.remove(document);

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
    public void saveItem(Delivery delivery) {
        List<ItemsDelivery>itemsDeliveries =delivery.getItemdeliveries();
        if(delivery.getDocument().getId() == 1 || delivery.getDocument().getId() == 4){
            for (int a = 0; a < itemsDeliveries.size(); a++) {
                stateProductsRepository.checkStateProduct(itemsDeliveries.get(a).getProduct(),
                        itemsDeliveries.get(a).getQuantity(), 1L);
                em.merge(delivery);

            }
        }
        if (delivery.isConfirm()){
if (delivery.getDocument().getId() == 3){
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

    }}}}
