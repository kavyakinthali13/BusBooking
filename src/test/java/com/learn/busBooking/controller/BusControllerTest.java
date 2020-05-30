package com.learn.busBooking.controller;
  
  import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.learn.busBooking.Dto.BusDto;
import com.learn.busBooking.Dto.BusRouteDto;
import com.learn.busBooking.Dto.ResponseDto;
import com.learn.busBooking.exception.BusNotFoundException;
import com.learn.busBooking.exception.UserNotAdmin;
import com.learn.busBooking.exception.UserNotFound;
import com.learn.busBooking.model.Bus;
import com.learn.busBooking.model.User;
import com.learn.busBooking.repository.BusRepository;
import com.learn.busBooking.repository.UserRepository;
import com.learn.busBooking.service.BusService;
import com.learn.busBooking.service.UserService;

import junit.framework.Assert;
  
  @RunWith(MockitoJUnitRunner.Silent.class)
  
  public class BusControllerTest {
  
  @InjectMocks
  BusController busController;
  
  @Mock
  BusService busService;
  
  @Mock
  UserService userService;
  
  @Mock
  UserRepository userRepository;
  
  @Test
  public void testFindBySourceAndDestinationAndDateForPostive() throws
  BusNotFoundException {
  
  List<Bus> buses = new ArrayList<Bus>();
  
  BusRouteDto busRouteDto = new BusRouteDto();
  
  busRouteDto.setSource("srikakulam");
  
  busRouteDto.setDestination("hyderabad");
  
  busRouteDto.setDepatureDate("2020-04-13");
  
  Mockito.when(busService.findBySourceAndDestinationAndDepatureDate(
  busRouteDto)).thenReturn(buses);
  
  ResponseEntity<List<Bus>> bus1 = busController.viewBuses(busRouteDto);
  
  Assert.assertNotNull(bus1);
  
  Assert.assertEquals(busRouteDto.getDestination(), "hyderabad");
  
  }
  
  @Test(expected = Exception.class)
  
  public void testFindBySourceAndDestinationAndDateForExce() throws
  BusNotFoundException {
  
  List<Bus> buses = new ArrayList<Bus>();
  
  BusRouteDto busRouteDto = new BusRouteDto();
  
  busRouteDto.setSource("srikakulam");
  
  busRouteDto.setDestination("hyderabad");
  
  busRouteDto.setDepatureDate("2020-04-13");
  
  Mockito.when(busService.findBySourceAndDestinationAndDepatureDate(
  busRouteDto)) .thenThrow(Exception.class);
  
  ResponseEntity<List<Bus>> bus1 = busController.viewBuses(busRouteDto);
  
  Assert.assertNotNull(bus1);
  
  Assert.assertEquals(busRouteDto.getDestination(), "vizag");
  
  }
  
  @Test
  
  public void testFindBySourceAndDestinationAndDateForNegative() throws
  BusNotFoundException {
  
  List<Bus> buses = new ArrayList<Bus>();
  
  BusRouteDto busRouteDto = new BusRouteDto();
  
  busRouteDto.setSource("srikakulam");
  
  busRouteDto.setDestination("hyderabad");
  
  busRouteDto.setDepatureDate("2020-04-13");
  
  busRouteDto.setSource("hyderabad");
  
  busRouteDto.setDestination("chennai");
  
  busRouteDto.setDepatureDate("2020-04-13");
  
  Mockito.when(busService.findBySourceAndDestinationAndDepatureDate(
  busRouteDto)).thenReturn(buses);
  
  ResponseEntity<List<Bus>> bus1 = busController.viewBuses( busRouteDto);
  
  Assert.assertNotNull(bus1);
  
  Assert.assertEquals(busRouteDto.getDestination(), "chennai");
  
  }
  
  @Test(expected=UserNotFound.class)
  public void testCreateBusForException() {
	  User user = new User();
	  user.setId(1L);
	  user.setEmail("gs@gmail.com");
	  user.setActivated(true);
	  user.setAdmin(true);
	  user.setFirstname("gs");
	  userRepository.save(user);
	  BusDto busDto = new BusDto();
	  busDto.setArrivalDate(Date.valueOf("2020-05-21"));
	  busDto.setAvailableTickets(25);
	  busDto.setDepartureDate(Date.valueOf("2020-05-19"));
	  busDto.setDestination("pune");
	  busDto.setSource("sklm");
	  busDto.setTotalSeats(30);
	  ResponseDto busDto1=new ResponseDto();
	  User user1 = new User();
	  user1=userRepository.findById(2L).orElseThrow(()->new UserNotFound("notfound"));
	  if(user1.isAdmin()) {
			busDto1=busService.createBus(busDto);
			new ResponseEntity<ResponseDto>(busDto1,HttpStatus.OK);
		}
		
	  ResponseEntity<ResponseDto> busDto2=new ResponseEntity<ResponseDto>(HttpStatus.OK);
	  busDto2=busController.createBus(1, busDto);
	  Assert.assertEquals(busDto2, busDto1);
  }
  @Test
  public void testCreateBus() {
	  User user = new User();
	  user.setId(1L);
	  user.setEmail("gs@gmail.com");
	  user.setActivated(true);
	  user.setAdmin(true);
	  user.setFirstname("gs");
	  userRepository.save(user);
	  BusDto busDto = new BusDto();
	  busDto.setArrivalDate(Date.valueOf("2020-05-21"));
	  busDto.setAvailableTickets(25);
	  busDto.setDepartureDate(Date.valueOf("2020-05-19"));
	  busDto.setDestination("pune");
	  busDto.setSource("sklm");
	  busDto.setTotalSeats(30);
	  ResponseDto busDto1=new ResponseDto();
	  Mockito.when(userService.getUserById(1L)).thenReturn(user);
	  if(user.isAdmin()) {
			Mockito.when(busService.createBus(busDto)).thenReturn(busDto1);
		}
		ResponseEntity<ResponseDto> busDto2=busController.createBus(1, busDto);
	  Assert.assertEquals(busDto2.getBody(), busDto1);
  }
  
  @Test
  public void testUpdateBus() {
	  User user = new User();
	  user.setId(1L);
	  user.setEmail("gs@gmail.com");
	  user.setActivated(true);
	  user.setAdmin(true);
	  user.setFirstname("gs");
	  userRepository.save(user);
	  ResponseDto busDto = new ResponseDto();
	  busDto.setArrivalDate(Date.valueOf("2020-05-21"));
	  busDto.setAvailableTickets(25);
	  busDto.setDepartureDate(Date.valueOf("2020-05-19"));
	  busDto.setDestination("pune");
	  busDto.setSource("sklm");
	  busDto.setTotalSeats(30);
	  ResponseDto busDto1=new ResponseDto();
	  Mockito.when(userService.getUserById(1L)).thenReturn(user);
	  if(user.isAdmin()) {
			Mockito.when(busService.updateBus(busDto)).thenReturn(busDto1);
		}
		ResponseEntity<ResponseDto> busDto2=busController.updateBus(1, busDto);
	  Assert.assertEquals(busDto2.getBody(), busDto1);
  }
  @Test
  public void testDeleteBus() {
	  User user = new User();
	  user.setId(1L);
	  user.setEmail("gs@gmail.com");
	  user.setActivated(true);
	  user.setAdmin(true);
	  user.setFirstname("gs");
	  userRepository.save(user);
	  BusDto busDto = new BusDto();
	  busDto.setArrivalDate(Date.valueOf("2020-05-21"));
	  busDto.setAvailableTickets(25);
	  busDto.setDepartureDate(Date.valueOf("2020-05-19"));
	  busDto.setDestination("pune");
	  busDto.setSource("sklm");
	  busDto.setTotalSeats(30);
	  ResponseDto busDto1=new ResponseDto();
	  Mockito.when(userService.getUserById(1L)).thenReturn(user);
	  if(user.isAdmin()) {
			busService.deleteBus(1L);
		}
		
  }
  
  
  }
  
 