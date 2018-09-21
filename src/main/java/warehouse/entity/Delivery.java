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
    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;
    private String description;
    @Temporal(TemporalType.DATE)
    private Date date;
    private String nameUser;
    @OneToMany(mappedBy = "delivery",cascade = CascadeType.ALL)
    @Column(nullable = true)
    private List<ItemsDelivery> itemdeliveries;
    @OneToOne
    private Document document;
    @OneToOne
    private Delivery deliverySecond;

    public Delivery() {
        this.itemdeliveries = new ArrayList<>();
        this.date = new Date();
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

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
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

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public double totalPrice(List<ItemsDelivery> itemsDeliveries){
        double sum =itemsDeliveries.stream().map(ItemsDelivery::getTotalPrice).reduce(Double::sum).get();
        return sum;
    }

    public Delivery getDelivery() {
        return deliverySecond;
    }

    public void setDelivery(Delivery deliverySecond) {
        this.deliverySecond = deliverySecond;
    }

    @Override
    public String toString() {
        return "Delivery{" +
                "id=" + id +
                ", warehouse=" + warehouse +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", nameUser='" + nameUser + '\'' +
                ", itemdeliveries=" + itemdeliveries +
                ", document=" + document +
                ", deliverySecond=" + deliverySecond +
                '}';
    }
}
