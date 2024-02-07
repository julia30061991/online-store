package com.onlinestore.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "orders")
public class Order implements Serializable {
    @Id
    @Column(name = "order_id", columnDefinition = "INT", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.UserDetails.class)
    private int id;
    @ElementCollection
    @CollectionTable(name="items_list", joinColumns = @JoinColumn(name = "order_id"))
    @Column(name = "all_items")
    @JsonView(Views.UserDetails.class)
    public List<String> items;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @JsonView(Views.UserDetails.class)
    private StatusEnum status;
    @Column(name = "price", columnDefinition = "NUMERIC")
    @JsonView(Views.UserDetails.class)
    private double price;
    @ManyToOne (fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "id")
    private User personUser;

    @Override
    public String toString() {
        return "Номер заказа: " + id + ", статус заказа: " + status + ", общая стоимость товаров: " + price;
    }
}