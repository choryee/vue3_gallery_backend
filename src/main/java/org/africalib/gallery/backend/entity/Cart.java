package org.africalib.gallery.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

// db의 item table과 연동하려로
@Table(name = "carts")
@Getter
@Setter
@Entity
public class Cart { //42'00
// Getter만으로도, JPA방식으로 가져오게 하는 듯.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private int memberId;

    @Column
    private int itemId;

}
