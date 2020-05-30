package com.learn.busBooking.controller;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.learn.busBooking.Dto.ResponseDto;
import com.learn.busBooking.Dto.TicketDto;
import com.learn.busBooking.Dto.TicketResponseDto;
import com.learn.busBooking.Dto.UserResponseDto;
import com.learn.busBooking.exception.AccountDetailsNotFoundException;
import com.learn.busBooking.exception.BookingDetailsNotFoundException;
import com.learn.busBooking.exception.InsufficientBalance;
import com.learn.busBooking.exception.SeatsNotAvailbleException;
import com.learn.busBooking.service.TicketService;

@RestController
public class TicketController {
	
	@Autowired
	TicketService ticketService;
	
		
	@PostMapping("/bookTicket")
	public ResponseEntity<UserResponseDto> ticketBooking(@RequestBody TicketDto ticketDto) throws BookingDetailsNotFoundException, SeatsNotAvailbleException, AccountDetailsNotFoundException, InsufficientBalance{
		UserResponseDto ticketResponseDto=new UserResponseDto();
		ticketService.saveTicket(ticketDto);
		ticketResponseDto.setMessage("tickets booked successfully");
		ticketResponseDto.getMessage();
		return new ResponseEntity<UserResponseDto>(ticketResponseDto, HttpStatus.OK);	
	}

	
}
