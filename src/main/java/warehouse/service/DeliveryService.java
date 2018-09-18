package warehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import warehouse.entity.Delivery;
import warehouse.entity.ItemsDelivery;
import warehouse.repository.DeliveryRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeliveryService {
    @Autowired
    private DeliveryRepository deliveryRepository;

    public double totalPrice(List<ItemsDelivery> itemsDeliveries){
        double sum =itemsDeliveries.stream().map(ItemsDelivery::getTotalPrice).reduce(Double::sum).get();
        return sum;
    }
    public List<Delivery> sortedDelivery(String sort) {
        List<Delivery> deliveries = deliveryRepository.findAll();
        if (sort == null){
            return deliveries;
        }else if(sort.equals("type")) {
            return deliveries.stream().sorted(Comparator.comparing(Delivery::getDescription)).collect(Collectors.toList());
        } else if (sort.equals("date")) {
            return deliveries.stream().sorted(Comparator.comparing(Delivery::getDate)).collect(Collectors.toList());
        } else if (sort.equals("user")) {
            return deliveries.stream().sorted(Comparator.comparing(a -> a.getUser().getName())).collect(Collectors.toList());
        }
        return deliveries;
    }
}
