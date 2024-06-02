package com.lifepill.posorderservice.entity;

import com.fasterxml.jackson.annotation.JsonTypeId;
import jakarta.persistence.*;
import lombok.*;

/**
 * The type Order details.
 */
@Entity
@Table(name = "order_details")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OrderDetails extends BaseEntity{
    @Id
    @Column(name = "order_details_id",length = 45)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int orderDetailsId;

    @Column(name = "name",length = 100,nullable = false)
    private String name;

    @Column(name = "amount",length = 100,nullable = false)
    private Double amount;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order orders;

/*    @ManyToOne
    @JoinColumn(name = "item_id",nullable = false)
    private Item items;*/

}