package com.software_engineering.booksys_ver2.table.domain;

import com.software_engineering.booksys_ver2.booking.domain.Booking;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@jakarta.persistence.Table(name = "tables")
@Getter @Setter
public class Table {

  @Id
  @GeneratedValue
  private Long id;

  private int tableNumber; // 테이블 번호

  private int customerCount; // 식사 인원

  @OneToOne(fetch = LAZY)
  private Booking booking; // 테이블에 할당된 예약
}
