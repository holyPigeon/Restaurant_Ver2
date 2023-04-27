package com.software_engineering.booksys_ver2.customer.domain;

import com.software_engineering.booksys_ver2.booking.domain.Booking;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Vector;

@Entity
@Table(name = "customers")
@Getter @Setter
public class Customer {

  @Id @GeneratedValue
  @Column(name="customer_id")
  private Long id;

  private String name; // 고객 이름

  private String phoneNumber; // 고객 전화번호 ("-" 을 제외한)

  @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
  private List<Booking> bookings = new Vector<>(); // 고객 예약 목록

}
