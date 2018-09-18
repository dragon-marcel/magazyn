package warehouse.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "delivery" )
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    @Temporal(TemporalType.DATE)
    private Date date;
    @OneToOne
    private User user;
    @OneToMany(mappedBy = "delivery",cascade = CascadeType.ALL)
    @Column(nullable = true)
    private List<ItemsDelivery> itemdeliveries;

    public Delivery() {
        this.itemdeliveries = new ArrayList<>();
    }

    public Delivery(String description, User user) {
        this.description = description;
        this.date = new Date();
        this.user = user;
        this.itemdeliveries = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<ItemsDelivery> getItemdeliveries() {
        return itemdeliveries;
    }

    public void setItemdeliveries(List<ItemsDelivery> itemdeliveries) {
        this.itemdeliveries = itemdeliveries;
    }
    public void addItemsDelivery(ItemsDelivery itemsDelivery){
        itemdeliveries.add(itemsDelivery);
    }

    @Override
    public String toString() {
        return "Delivery{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", user=" + user +
                ", itemdeliveries=" + itemdeliveries +
                '}';
    }

    public double totalPrice(List<ItemsDelivery> itemsDeliveries){
        double sum =itemsDeliveries.stream().map(ItemsDelivery::getTotalPrice).reduce(Double::sum).get();
        return sum;
    }

}
