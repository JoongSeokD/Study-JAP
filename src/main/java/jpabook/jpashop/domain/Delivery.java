package jpabook.jpashop.domain;

import javax.persistence.*;

@Entity
public class Delivery extends BaseEntity{

    @Id @GeneratedValue
    @Column(name = "DELIVERY_ID")
    private Long id;
    @Enumerated(EnumType.STRING)
    private DeliveryStatus ststus;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;



}
