package com.software_engineering.booksys_ver2.booking.presentation;

import com.software_engineering.booksys_ver2.booking.application.BookingService;
import com.software_engineering.booksys_ver2.booking.domain.Booking;
import com.software_engineering.booksys_ver2.booking.presentation.dto.request.CreateReservationRequest;
import com.software_engineering.booksys_ver2.booking.presentation.dto.request.CreateWalkInRequest;
import com.software_engineering.booksys_ver2.booking.presentation.dto.request.UpdateBookingRequest;
import com.software_engineering.booksys_ver2.booking.presentation.dto.response.BookingListResponse;
import com.software_engineering.booksys_ver2.booking.presentation.dto.response.BookingResponse;
import com.software_engineering.booksys_ver2.customer.application.CustomerService;
import com.software_engineering.booksys_ver2.table.application.TableService;
import com.software_engineering.booksys_ver2.table.domain.Table;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BookingController {

  private final BookingService bookingService;
  private final CustomerService customerService;
  private final TableService tableService;

  /**
   * 온라인 예약 생성
   */
  @PostMapping("/reservation")
  public ResponseEntity<Void> createReservation(@RequestBody CreateReservationRequest request) {

    Long customerId = customerService.createCustomer(request.getName(), request.getPhoneNumber());
    Table table = tableService.findByTableNumber(request.getTableNumber());
    Long tableId = table.getId();

    bookingService.createReservation(customerId, 1L, tableId, request.getBookingDateTime(), request.getCovers(), request.getPassword());

    return ResponseEntity.noContent().build();
  }

  /**
   * 현장 에약 생성
   */
  @PostMapping("/walkIn")
  public ResponseEntity<Void> createWalkIn(@RequestBody CreateWalkInRequest request) {

    Long customerId = customerService.createCustomer(request.getName(), request.getPhoneNumber());
    Table table = tableService.findByTableNumber(request.getTableNumber());
    Long tableId = table.getId();

    bookingService.createWalkIn(customerId, 1L, tableId, request.getCovers(), request.getPassword());

    return ResponseEntity.noContent().build();
  }

  /**
   * 개별 예약 조회
   */
  @GetMapping("/booking/{bookingId}")
  public BookingResponse getBooking(@PathVariable Long bookingId) {

    Booking booking = bookingService.findById(bookingId);

    return new BookingResponse(booking.getId(), booking.getCustomer().getName(),
        booking.getCustomer().getPhoneNumber(), booking.getTable().getTableNumber(),
        booking.getTable().getPlaces(), booking.getBookingStatus(),
        booking.getBookingDateTime(), booking.getArrivalDateTime());
  }

  /**
   * 전체 예약 조회
   */
  @GetMapping("/booking")
  public BookingListResponse<List<BookingResponse>> getBookingList() {

    List<Booking> bookingList = bookingService.findAll();

    List<BookingResponse> bookingListResponse =
        bookingList.stream().map(booking -> new BookingResponse(
            booking.getId(), booking.getCustomer().getName(),
            booking.getCustomer().getPhoneNumber(), booking.getTable().getTableNumber(),
            booking.getCovers(), booking.getBookingStatus(),
            booking.getBookingDateTime(), booking.getArrivalDateTime()
        )).collect(Collectors.toList());

    return new BookingListResponse<>(bookingListResponse.size(), bookingListResponse);
  }

  /**
   * 예약 변경
   */
  @PatchMapping("/booking/{bookingId}")
  public ResponseEntity<Void> updateBooking(@PathVariable Long bookingId, @RequestBody UpdateBookingRequest request) {

    Booking booking = bookingService.findById(bookingId);
    bookingService.updateBooking(bookingId, request.getTableNumber(),
        request.getCovers(), request.getBookingDateTime());

    return ResponseEntity.noContent().build();
  }

  /**
   * 예약 취소(삭제)
   */
  @DeleteMapping("/booking/{bookingId}")
  public ResponseEntity<Void> deleteBooking(@PathVariable Long bookingId) {

    bookingService.deleteBooking(bookingId);

    return ResponseEntity.noContent().build();
  }
}
