package org.africalib.gallery.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

// db의 members table과 연동하려로
@Table(name = "members")
@Getter
@Entity
public class Member { //01'27'00
// Getter만으로도, JPA방식으로 가져오게 하는 듯.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 50, nullable = false, unique = true)
    private String email;

    @Column(length = 100, nullable = false)
    private String password; // img_path 컬럼명도 매칭됨.

    @Column(length = 100)
    private LocalDateTime reg_date;



}
