package com.software_engineering.booksys_ver2.booking.domain;

import com.software_engineering.booksys_ver2.booking.domain.enums.BookingStatus;
import com.software_engineering.booksys_ver2.customer.domain.Customer;
import com.software_engineering.booksys_ver2.restaurant.domain.Restaurant;
import com.software_engineering.booksys_ver2.table.domain.Table;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Access(AccessType.FIELD)
@DiscriminatorValue("RESERVATION")
public class Reservation extends Booking {
  @Override
  public String getDetails() {
    StringBuilder details = new StringBuilder(64);
    details.append(getCustomer().getName());
    details.append(" ");
    details.append(getCustomer().getPhoneNumber());
    details.append(" (");
    details.append(getTable().getCustomerCount());
    details.append("명)");
    if (getArrivalDateTime() != null) {
      details.append(" [");
      details.append(getArrivalDateTime());
      details.append("]");
    }

    return details.toString();
  }

  public static Reservation createReservation(Customer customer, Restaurant restaurant, Table table, LocalDateTime dateTime) {

    Reservation reservation = new Reservation();
    reservation.setBookingStatus(BookingStatus.BOOK);
    reservation.setBookingDateTime(dateTime);
//  reservation.setArrivalDateTime(); -> 도착 시간은 예약 생성 시점이 아닌 손님 도착 시점에 설정
    reservation.setCustomer(customer);
    reservation.setRestaurant(restaurant);
    reservation.setTable(table);

    return reservation;
  }

  //== 비즈니스 로직 ==//

  /**
   * 예약 취소
   */
  public void cancel() {
    LocalDateTime currentDateTime = LocalDateTime.now();
    LocalDateTime reservationDateTime = getBookingDateTime();

    if (currentDateTime.isAfter(reservationDateTime.minusMinutes(30))) { // 예약시간 30분 전까지는 취소 가능, 예약 시간 임박 시에는 취소 불가
      throw new IllegalStateException("예약 시간 임박으로 취소할 수 없습니다.");
    }

    this.setBookingStatus(BookingStatus.CANCEL); // 예약상태 취소로 변경
  }
}
