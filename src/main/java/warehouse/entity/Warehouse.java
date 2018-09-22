package warehouse.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "warehouse")
    private List<Delivery> delivery;
    @OneToOne
    private StateProducts stateProducts;


    public Warehouse() {
        this.delivery = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Delivery> getDelivery() {
        return delivery;
    }

    public void setDelivery(List<Delivery> delivery) {
        this.delivery = delivery;
    }

    public StateProducts getStateProducts() {
        return stateProducts;
    }

    public void setStateProducts(StateProducts stateProducts) {
        this.stateProducts = stateProducts;
    }

    @Override
    public String toString() {
        return "Warehouse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", delivery=" + delivery +
                ", stateProducts=" + stateProducts +
                '}';
    }
}
