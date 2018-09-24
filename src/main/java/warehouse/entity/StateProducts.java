package warehouse.entity;

import javax.persistence.*;
import java.util.List;


@Entity
public class StateProducts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Product products;
    private Long quantity;
    @OneToOne
    private Warehouse warehouse;

    public StateProducts() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public Long getQuantity() {
        return quantity;
    }

    public Product getProduct() {
        return products;
    }

    public void setProduct(Product product) {
        this.products = product;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Product getProducts() {
        return products;
    }

    public void setProducts(Product products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "StateProducts{" +
                "id=" + id +
                ", products=" + products +
                ", quantity=" + quantity +
                ", warehouse=" + warehouse +
                '}';
    }
}
