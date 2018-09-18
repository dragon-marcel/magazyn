package warehouse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import warehouse.entity.Delivery;
import warehouse.entity.ItemsDelivery;
import warehouse.entity.Product;
import warehouse.entity.User;
import warehouse.repository.DeliveryRepository;
import warehouse.repository.ProductRepository;
import warehouse.repository.UserInterface;
import warehouse.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Date;

@Component
public class StartApp implements CommandLineRunner {
@Autowired
private DeliveryRepository deliveryRepository;

//static EntityManagerFactory factory = Persistence.createEntityManagerFactory("ClockworkPersistence");
//static EntityManager manager =factory.createEntityManager();
    @Override
    public void run(String... args) throws Exception {


    }
}
