package com.lifepill.posorderservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;


@Entity
@Table(name = "orders")
//@TypeDefs({
//        @TypeDef(name = "json", typeClass = JsonNode.class)
//})

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order {
    @Id
    @Column(name = "order_id", length = 45)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int orderId;

//    @ManyToOne
//    @JoinColumn(name = "cashier_id",nullable = false)
//    private Cashier cashiers;

    @Column(name = "order_date", columnDefinition = "TIMESTAMP")
    private Date orderDate;

    @Column(name = "total",nullable = false)
    private Double total;

    @OneToMany(mappedBy = "orders")
    private Set<OrderDetails> orderDetails;

//    public Order(Cashier cashiers, Date orderDate, Double total) {
//        this.cashiers = cashiers;
//        this.orderDate = orderDate;
//        this.total = total;
//    }
}