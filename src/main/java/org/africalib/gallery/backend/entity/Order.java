package org.africalib.gallery.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

// db의 item table과 연동하려로
@Table(name = "orders")
@Getter
@Setter
@Entity
public class Order { //42'00
// Getter만으로도, JPA방식으로 가져오게 하는 듯.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private int memberId;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 500, nullable = false)
    private String address;

    @Column(length = 10, nullable = false)
    private String payment;

    @Column(length = 16)
    private String cardNumber;

    @Column(length = 500, nullable = false)
    private String items;

    @Column(length = 500)
    private LocalDateTime reg_date;


}
