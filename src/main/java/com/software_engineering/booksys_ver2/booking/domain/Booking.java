package com.software_engineering.booksys_ver2.booking.domain;

import com.software_engineering.booksys_ver2.booking.domain.enums.BookingStatus;
import com.software_engineering.booksys_ver2.customer.domain.Customer;
import com.software_engineering.booksys_ver2.restaurant.domain.Restaurant;
import com.software_engineering.booksys_ver2.table.domain.Table;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="DTYPE")
public abstract class Booking {

  @Id @GeneratedValue
  private Long id;

  private LocalDateTime bookingDateTime; // 예약 날짜 및 시간

  private LocalDateTime arrivalDateTime; // 고객 도착 날짜 및 시간

  @Enumerated(EnumType.STRING)
  private BookingStatus bookingStatus;

  @OneToOne(fetch = LAZY)
  private Table table; // 예약한 테이블

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "customer_id")
  private Customer customer; // 예약한 고객

  @ManyToOne(fetch = LAZY)
  private Restaurant restaurant; // 예약한 레스토랑

  //== 조회 메소드 ==//

  /**
   * 예약 종료시간 조회
   */
  public LocalDateTime getEndDateTime() {
    LocalDateTime currentDateTime = bookingDateTime;
    return currentDateTime.plusHours(2);
  }

  /**
   * 예약 상세정보 조회
   */
  public abstract String getDetails();

  //== 연관관계 편의 메소드 ==//
  public void setCustomer(Customer customer) {
    this.customer = customer;
    customer.getBookings().add(this);
  }
  public void setRestaurant(Restaurant restaurant) {
    this.restaurant = restaurant;
    restaurant.getBookings().add(this);
  }
}
