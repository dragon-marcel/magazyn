package warehouse.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import warehouse.dao.ProductDAO;
import warehouse.entity.Product;
import warehouse.entity.StateProducts;
import warehouse.entity.Warehouse;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
@Repository
public class ProductRepository implements ProductInterface {
    @Autowired
    private ProductDAO productDAO;
    @Autowired
    private WarehouseRepository warehouseRepository;
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void save(Product product) {

        Warehouse warehouseMain = warehouseRepository.findOnebyId(1L);
        Warehouse warehouseShop = warehouseRepository.findOnebyId(2L);

        StateProducts stateProductsMain = new StateProducts();
        stateProductsMain.setQuantity(0L);
        stateProductsMain.setProduct(product);
        stateProductsMain.setWarehouse(warehouseMain);

        StateProducts stateProductsShop = new StateProducts();
        stateProductsShop.setProduct(product);
        stateProductsShop.setQuantity(0L);
        stateProductsShop.setWarehouse(warehouseShop);

        em.persist(product);
        em.persist(stateProductsMain);
        em.persist(stateProductsShop);

    }

    @Override
    public List<Product> findall() {
        return(List<Product>)productDAO.findAll();
    }

    public Product findOnebyId(Long id){
        return em.find(Product.class,id);
    }
}
