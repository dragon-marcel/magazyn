package warehouse.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.core.EntityMetadata;
import org.springframework.stereotype.Repository;
import warehouse.dao.StateProductsDAO;
import warehouse.entity.StateProducts;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
@Repository
public class StateProductsRepository implements StateProductsInterface{
    @Autowired
    private StateProductsDAO stateProductsDAO;
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<StateProducts> findAllStateProductsbyWarehouse(Long id) {

        List<StateProducts>stateProducts =
                em.createQuery("select s from StateProducts s where s.warehouse.id =:id order by s.quantity DESC ").
                        setParameter("id",id).getResultList();

        return stateProducts;
    }
}
