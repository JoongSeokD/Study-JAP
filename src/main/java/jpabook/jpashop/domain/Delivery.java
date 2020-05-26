package jpabook.jpashop.domain;

import javax.persistence.*;

@Entity
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "DELIVERY_ID")
    private Long id;
    @Enumerated(EnumType.STRING)
    private DeliveryStatus ststus;

    @OneToOne(mappedBy = "delivery")
    private Order order;

    private String street;
    private String city;
    private String zipcode;


}
