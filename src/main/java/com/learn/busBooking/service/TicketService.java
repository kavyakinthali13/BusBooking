package com.learn.busBooking.service;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.learn.busBooking.Dto.TicketDto;
import com.learn.busBooking.exception.AccountDetailsNotFoundException;
import com.learn.busBooking.exception.BookingDetailsNotFoundException;
import com.learn.busBooking.exception.BusNotFoundException;
import com.learn.busBooking.exception.InsufficientBalance;
import com.learn.busBooking.exception.SeatsNotAvailbleException;
import com.learn.busBooking.exception.TicketsNotAvailableException;
import com.learn.busBooking.exception.UserNotFound;
import com.learn.busBooking.exception.UserNotLoggedIn;
import com.learn.busBooking.model.Bus;
import com.learn.busBooking.model.Ticket;
import com.learn.busBooking.model.User;
import com.learn.busBooking.repository.BusRepository;
import com.learn.busBooking.repository.TicketRepository;
import com.learn.busBooking.repository.UserRepository;

@Service
public class TicketService {
	@Autowired
	TicketRepository ticketRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	BusRepository busRepository;
	

	@Autowired
	RestTemplate restTemplate;

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	Bus bus = new Bus();

	public Ticket saveTicket(TicketDto ticketDto) throws BookingDetailsNotFoundException, SeatsNotAvailbleException, AccountDetailsNotFoundException, InsufficientBalance {
		if (ticketDto.getUser().getId() != null && ticketDto.getBus().getId() != null) {
			Ticket ticket = new Ticket();
			BeanUtils.copyProperties(ticketDto, ticket);
			bus = busRepository.findById(ticket.getBus().getId()).get();

			if (bus.getAvailableTickets() > ticketDto.getNumberOfTickets()) {
				bus.setAvailableTickets(bus.getAvailableTickets() - ticketDto.getNumberOfTickets());
				paymentForBusTicket(ticketDto, bus);
				busRepository.save(bus);
				return busRepository.save(ticket);
			} else {
				throw new SeatsNotAvailbleException(
						"please enter no of tickets less than or equal to the NoOfSeatsAvailable ");
			}

		} else {
			throw new BookingDetailsNotFoundException("Missing busId or  UserId");
		}

	}

	public String paymentForBusTicket(TicketDto ticketDto, Bus bus) throws AccountDetailsNotFoundException, InsufficientBalance {
		if (ticketDto.getAccountNumber() != 0 && ticketDto.getBeneficiaryAccountNumber() != 0) {
			int amount = (int) (ticketDto.getNumberOfTickets() * bus.getPricePerticket());
			String amount1 = Integer.toString(amount);
			String url = "http://localhost:8055/transaction";
			String beneficiaryAccountNumber = "" + ticketDto.getBeneficiaryAccountNumber();
			String accountNumber = "" + ticketDto.getAccountNumber();

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

			Map<String, String> params = new HashMap<String, String>();
			params.put("accountNumber", accountNumber);
			params.put("beneficiaryAccountNumber", beneficiaryAccountNumber);
			params.put("amount", amount1);
			params.put("customerId", "1");

			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
			for (Map.Entry<String, String> entry : params.entrySet()) {
				builder.queryParam(entry.getKey(), entry.getValue());
			}
			try {

			String result = restTemplate.getForObject(builder.toUriString(), String.class);
			return result;
			}
			catch(Exception e) {
				throw new InsufficientBalance("entered amount is greater than the availble balance");
				
			}
		} else {
			throw new AccountDetailsNotFoundException("enter account correct number");
		}

	}

}
	
