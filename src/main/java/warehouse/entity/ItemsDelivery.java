package warehouse.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "items")
public class ItemsDelivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    private int quantity;
    @ManyToOne
    private Delivery delivery;

    public ItemsDelivery(Product product, int quantity, Delivery delivery) {
        this.product = product;
        this.quantity = quantity;
        this.delivery = delivery;
    }

    public ItemsDelivery() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }
    public double getTotalPrice(){
        return getQuantity()*getProduct().getPrice();
    }
    @Override
    public String toString() {
        return "ItemsDelivery{" +
                "id=" + id +
                ", product=" + product +
                ", quantity=" + quantity +
                ", delivery=" + delivery +
                '}';
    }
}
