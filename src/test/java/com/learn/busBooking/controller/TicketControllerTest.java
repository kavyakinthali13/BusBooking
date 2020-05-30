
 package com.learn.busBooking.controller;
  
  import java.util.ArrayList; import java.util.List;
  
  import org.junit.Test; import org.junit.runner.RunWith; import
  org.mockito.InjectMocks; import org.mockito.Mock; import org.mockito.Mockito;
  import org.mockito.junit.MockitoJUnitRunner; import
  org.springframework.http.ResponseEntity;
  
  import com.learn.busBooking.Dto.TicketDto; import
  com.learn.busBooking.Dto.TicketResponseDto;
import com.learn.busBooking.Dto.UserResponseDto;
import com.learn.busBooking.exception.AccountDetailsNotFoundException;
import com.learn.busBooking.exception.BookingDetailsNotFoundException;
import com.learn.busBooking.exception.InsufficientBalance;
import com.learn.busBooking.exception.SeatsNotAvailbleException;
import
  com.learn.busBooking.model.Bus; import com.learn.busBooking.model.Ticket;
  import com.learn.busBooking.model.User; import
  com.learn.busBooking.repository.BusRepository; import
  com.learn.busBooking.repository.UserRepository; import
  com.learn.busBooking.service.TicketService;
  
  import junit.framework.Assert;
  
  
  @RunWith(MockitoJUnitRunner.Silent.class) public class TicketControllerTest {
  
  @InjectMocks 
  TicketController ticketController;
  
  @Mock 
  TicketService ticketService;
  
  @Mock 
  UserRepository userRepository;
  
  @Mock
  BusRepository busRepository;
  
  
  @Test 
  public void testBookTicket() throws BookingDetailsNotFoundException, SeatsNotAvailbleException, AccountDetailsNotFoundException, InsufficientBalance { 
	  TicketResponseDto ticketResponseDto=new TicketResponseDto();
	  Ticket ticket = new Ticket(); 
	  TicketDto ticketDto = new TicketDto();
	  ticketDto.setId(1L); 
	  ticketDto.setId(2L);
  ticketDto.setNumberOfTickets(6); 
  List<Bus>buses=new ArrayList<>(); 
  Bus bus = new Bus(); 
  bus.setId(1L);
  bus.setSource("sklm"); 
  bus.setDestination("vizag");
  bus.setAvailableTickets(20);
  buses.add(bus);
  bus.setId(2L);
 bus.setSource("sklm");
 bus.setDestination("vizag");
  bus.setAvailableTickets(20);
  buses.add(bus);
  User user = new User();
  user.setId(20L);
  user.setFirstname("gsp");
  user.setLastname("chinnu");
  user.setEmail("hi@gm.cm");
  user.setActivated(true);
  user.setPhoneNumber("9876543210");
  userRepository.save(user);
 busRepository.save(bus);
  Mockito.when(ticketService.saveTicket((Mockito.any(TicketDto.class)))).thenReturn(ticket); 
  ResponseEntity<UserResponseDto> resTicket = ticketController.ticketBooking(ticketDto); 
  Assert.assertNotNull(resTicket);
  
  }
  
  @Test(expected = NullPointerException.class)
   public void testBookTicketForException() throws BookingDetailsNotFoundException, SeatsNotAvailbleException, AccountDetailsNotFoundException, InsufficientBalance { 
	  TicketDto ticket=new TicketDto();
  Mockito.when(ticketService.saveTicket(Mockito.any(TicketDto.class))).thenThrow(NullPointerException.class); 
  ResponseEntity<UserResponseDto>  resTicket = ticketController.ticketBooking(ticket);
  }
  
 
  }
 