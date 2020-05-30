package com.learn.busBooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learn.busBooking.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

}
