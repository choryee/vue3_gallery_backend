package org.africalib.gallery.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;

// db의 item table과 연동하려로
@Table(name = "items")
@Getter
@Entity
public class Item { //42'00
// Getter만으로도, JPA방식으로 가져오게 하는 듯.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 100)
    private String imgPath; // img_path 컬럼명도 매칭됨.

    @Column
    private int price;

    @Column
    private int discountPer;

}
