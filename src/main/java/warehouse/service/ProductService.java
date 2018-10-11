package warehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import warehouse.entity.Product;
import warehouse.repository.ProductRepository;

import java.util.List;
import java.util.function.Predicate;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    public boolean chackExistProductByName(Product product){
        List<Product> products = productRepository.findall();
        boolean check = products.stream().map(Product::getName).anyMatch(a->a.equals(product.getName()));
        return check;
        }
}
