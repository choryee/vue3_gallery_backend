package org.africalib.gallery.backend.dto;

import lombok.Getter;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
public class OrderDto {
    private String name;
    private String address;
    private String payment;
    private String cardNumber;
    private String items;
    private LocalDateTime  reg_date;
}
