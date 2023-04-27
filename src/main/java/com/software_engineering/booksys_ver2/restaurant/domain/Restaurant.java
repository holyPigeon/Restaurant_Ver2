package com.software_engineering.booksys_ver2.restaurant.domain;

import com.software_engineering.booksys_ver2.booking.domain.Booking;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Vector;

@Entity
@Getter @Setter
public class Restaurant {

  @Id
  @GeneratedValue

  private Long id;

  private int tableQuantity; // 해당 레스토랑의 테이블 갯수

  @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
  private List<Booking> bookings = new Vector<>(); // 해당 레스토랑의 예약 목록


}
