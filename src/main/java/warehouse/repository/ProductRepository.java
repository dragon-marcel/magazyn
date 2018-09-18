package warehouse.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import warehouse.dao.ProductDAO;
import warehouse.entity.Product;

import java.util.List;
@Repository
public class ProductRepository implements ProductInterface {
    @Autowired
    private ProductDAO productDAO;
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


}
