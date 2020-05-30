package com.learn.busBooking.service;
  import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.learn.busBooking.Dto.BusDto;
import com.learn.busBooking.Dto.BusRouteDto;
import com.learn.busBooking.Dto.ResponseDto;
import com.learn.busBooking.exception.BusNotFoundException;
import com.learn.busBooking.exception.UserNotFound;
import com.learn.busBooking.model.Bus;
import com.learn.busBooking.model.User;
import com.learn.busBooking.repository.BusRepository;
import com.learn.busBooking.repository.UserRepository; 
 
  
  @RunWith(MockitoJUnitRunner.Silent.class) 
  public class BusServiceTest {
  
  @InjectMocks 
  BusService busService;
  
  @Mock 
  BusRepository busRepository;
  
  @Test 
  public void tesfindBySourceAndDestinationAndDepatureDate() { 
	  List<Bus> buses=new ArrayList<>(); 
	  Bus bus = new Bus(); bus.setId(1L);
  bus.setSource("srikakulam"); 
  bus.setDestination("pune");
  bus.setArrivalDate(Date.valueOf("2020-05-21"));
  bus.setDepartureDate(Date.valueOf("2020-05-19"));
  bus.setAvailableTickets(30); 
  buses.add(bus); 
  bus.setId(2L);
  bus.setSource("srikakulam"); 
  bus.setDestination("pune");
  bus.setArrivalDate(Date.valueOf("2020-05-21")); 
  bus.setDepartureDate(Date.valueOf("2020-05-21"));
  bus.setAvailableTickets(30); 
  buses.add(bus);
  busRepository.saveAll(buses); 
  BusRouteDto busRouteDto = new BusRouteDto(); 
  busRouteDto.setSource("srikakulam");
  busRouteDto.setDestination("pune");
  busRouteDto.setDepatureDate("2020-05-12"); 
  OngoingStubbing<List<Bus>> buses1 = Mockito.when(busRepository.findBusBySourceAndDestinationAndDepartureDate(busRouteDto.getSource(),
  busRouteDto.getDestination(),busRouteDto.getDepatureDate())).thenReturn(buses
  ); 
  Assert.assertNotNull(buses1);
  
  }
  
  @Test(expected=BusNotFoundException.class) 
  public void tesfindBySourceAndDestinationAndDepatureDateForException() { 
	  List<Bus>  buses=new ArrayList<>(); 
	  Bus bus = new Bus(); bus.setId(1L);
  bus.setSource("srikakulam"); 
  bus.setDestination("pune");
  bus.setArrivalDate(Date.valueOf("2020-05-21"));  
  bus.setDepartureDate(Date.valueOf("2020-05-19")); 
  bus.setAvailableTickets(30); 
  buses.add(bus); 
  bus.setId(2L);
  bus.setSource("srikakulam"); 
  bus.setDestination("pune");
  bus.setArrivalDate(Date.valueOf("2020-05-21"));  
  bus.setDepartureDate(Date.valueOf("2020-05-19")); 
  bus.setAvailableTickets(30); 
  buses.add(bus);
  busRepository.saveAll(buses); 
  BusRouteDto busRouteDto = new BusRouteDto(); 
  busRouteDto.setSource("chennai");
  busRouteDto.setDestination("pune");
  busRouteDto.setDepatureDate("2020-05-12"); 
  OngoingStubbing<List<Bus>> buses1= Mockito.when(busRepository.findBusBySourceAndDestinationAndDepartureDate(busRouteDto.getSource(),
  busRouteDto.getDestination(),busRouteDto.getDepatureDate())).thenReturn(buses); 
  if(buses1.toString().isEmpty()) { 
	  throw new BusNotFoundException(); 
	  }
  throw new BusNotFoundException(); 
  }
  
  
  @Test(expected=IllegalArgumentException.class)
  public void testCreateBusForException() {
	 
	  BusDto busDto = new BusDto();
	  busDto.setArrivalDate(Date.valueOf("2020-05-21"));
	  busDto.setAvailableTickets(25);
	  busDto.setDepartureDate(Date.valueOf("2020-05-19"));
	  busDto.setDestination("pune");
	  busDto.setSource("sklm");
	  busDto.setTotalSeats(30);
	  ResponseDto busDto1=new ResponseDto();
	
			busDto1=busService.createBus(busDto);
			new ResponseEntity<ResponseDto>(busDto1,HttpStatus.OK);
	
		
	  ResponseDto busDto2=new ResponseDto();
	  busDto2=busService.createBus(busDto);
	  Assert.assertEquals(busDto2, busDto1);
  }


  
  @Test
  public void testDeleteBus() { 
	  Bus bus = new Bus(); 
	  bus.setId(1L);
	  bus.setSource("srikakulam"); 
	  bus.setDestination("pune");
	  bus.setArrivalDate(Date.valueOf("2020-05-21"));  
	  bus.setDepartureDate(Date.valueOf("2020-05-19")); 
	  bus.setAvailableTickets(30); 
	  busRepository.save(bus);
	busRepository.deleteById(1L);
		
  }
  
  }
 