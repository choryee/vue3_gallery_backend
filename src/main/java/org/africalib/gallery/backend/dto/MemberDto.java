package org.africalib.gallery.backend.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemberDto {
    private String email;
    private int password;
    private LocalDateTime  reg_date;
}
