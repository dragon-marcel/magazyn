package warehouse.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.core.EntityMetadata;
import org.springframework.stereotype.Repository;
import warehouse.dao.StateProductsDAO;
import warehouse.entity.Product;
import warehouse.entity.StateProducts;

import javax.persistence.*;
import java.util.List;
@Repository
public class StateProductsRepository implements StateProductsInterface{

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<StateProducts> findAllStateProductsbyWarehouse(Long id) {

        List<StateProducts>stateProducts =
                em.createQuery("select s from StateProducts s where s.warehouse.id =:id order by s.quantity DESC ").
                        setParameter("id",id).getResultList();

        return stateProducts;
    }

    @Override
    public void checkStateProduct(Product product, Long quantity, Long warehouseId) {
        List<StateProducts> stateProducts = findAllStateProductsbyWarehouse(warehouseId);
        stateProducts.stream()
                .filter(a->a.getProduct().getId().equals(product.getId()))
                .filter(a->{
                    if(a.getQuantity() < quantity ){
                        throw new NullPointerException();
                    } return true;}
                ).forEach(a->a.setQuantity(a.getQuantity()));
    }

    @Override
    public void subtractFromStateProducts(Product product, Long quantity, Long warehouseID) {
        List<StateProducts> stateProducts = findAllStateProductsbyWarehouse(warehouseID);
            stateProducts.stream()
                    .filter(a->a.getProduct().getId().equals(product.getId()))
                    .forEach(a->a.setQuantity(a.getQuantity() - quantity));
        for (StateProducts s:stateProducts){
            em.merge(s);
        }
    }
    @Override
    public void addtoStateProducts(Product product, Long quantity, Long warehouseID) {
        List<StateProducts> stateProducts = findAllStateProductsbyWarehouse(warehouseID);
        stateProducts.stream().filter(a -> a.getProduct().getId().equals(product.getId())).
                forEach(a -> a.setQuantity(a.getQuantity() + quantity));
        for (StateProducts s:stateProducts){
            em.merge(s);
        }
    }

}
