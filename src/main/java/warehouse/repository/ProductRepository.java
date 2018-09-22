package warehouse.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import warehouse.dao.ProductDAO;
import warehouse.entity.Product;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
@Repository
public class ProductRepository implements ProductInterface {
    @Autowired
    private ProductDAO productDAO;
    @PersistenceContext
    private EntityManager em;

    @Override
    public void save(Product product) {
        productDAO.save(product);
    }

    @Override
    public List<Product> findall() {
        return(List<Product>)productDAO.findAll();
    }

    @Override
    public void delete(Product product) {
        productDAO.delete(product);

    }

    @Override
    public Product find(Long id) {
        return productDAO.findById(id).orElse(null);
    }

    @Override
    public List<Product> productWarehouseMain() {
        // return em.createQuery("select product from items").getResultList();
        return  null;
    }

    @Override
    public List<Product> findProductByName(String term) {
        return productDAO.findProductByName(term);
    }


}
