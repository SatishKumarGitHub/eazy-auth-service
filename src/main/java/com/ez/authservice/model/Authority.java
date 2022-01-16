package com.ez.authservice.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "authorities")
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

/*    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;*/


}
