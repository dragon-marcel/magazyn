package warehouse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import warehouse.repository.DeliveryMainMainRepository;

@Component
public class StartApp implements CommandLineRunner {
@Autowired
private DeliveryMainMainRepository deliveryMainRepository;

    @Override
    public void run(String... args) throws Exception {


    }
}
