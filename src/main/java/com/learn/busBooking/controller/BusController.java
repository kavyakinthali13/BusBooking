package com.learn.busBooking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.learn.busBooking.Dto.BusDto;
import com.learn.busBooking.Dto.BusRouteDto;
import com.learn.busBooking.Dto.ResponseDto;
import com.learn.busBooking.exception.UserNotAdmin;
import com.learn.busBooking.model.Bus;
import com.learn.busBooking.model.User;
import com.learn.busBooking.service.BusService;
import com.learn.busBooking.service.UserService;

@RestController
public class BusController {
	@Autowired
	BusService busService;
	@Autowired
	UserService userService;
	@SuppressWarnings("unused")
	private ResponseDto busDto1=new ResponseDto();
	private User user = new User();
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createBus(@RequestParam long userId,@RequestBody BusDto busDto) {
		user=userService.getUserById(userId);
		if(user.isAdmin()) {
			busDto1=busService.createBus(busDto);
			return new ResponseEntity<ResponseDto>(busDto1,HttpStatus.OK);
		}
		else {
			throw new UserNotAdmin("invalid admin");
		}
	}
	
	@GetMapping("/findById")
	public ResponseEntity<ResponseDto> findBusById(@RequestParam long userId,@RequestParam long id) {
		user=userService.getUserById(userId);
		if(user.isAdmin()) {
			busDto1=busService.findBus(id);
			return new ResponseEntity<ResponseDto>(busDto1,HttpStatus.OK);
		}
		else {
			throw new UserNotAdmin("invalid admin");
		}
	}
	
	 @GetMapping("/bus/{source}/{destination}")
	  public ResponseEntity<List<Bus>> viewBuses(@PathVariable String source,@PathVariable String destination ) {
			 List<Bus> buses = busService.findBySourceAndDestination(source,destination); 
			 return new ResponseEntity<List<Bus>>(buses,HttpStatus.OK);
	  }
	 
	
	  @GetMapping("/searchbus")
	  public ResponseEntity<List<Bus>> viewBuses(BusRouteDto busRouteDto) {
			  List<Bus> buses = busService.findBySourceAndDestinationAndDepatureDate(busRouteDto); 
			  return new ResponseEntity<List<Bus>>(buses,HttpStatus.OK);
	  }
	  
	  @PutMapping("/update")
	  public ResponseEntity<ResponseDto> updateBus(@RequestParam long userId,@RequestBody ResponseDto busDto){
		  user=userService.getUserById(userId);
		  if(user.isAdmin()) {
			  busDto1=busService.updateBus(busDto);
			  return new ResponseEntity<ResponseDto>(busDto1,HttpStatus.OK);
			}
			else {
				throw new UserNotAdmin("invalid admin");
			}
	  }
	  
	  @DeleteMapping("/delete")
	  public ResponseEntity<String> deleteBus(@RequestParam long userId,@RequestParam long id) {
		  user=userService.getUserById(userId);
		  if(user.isAdmin()) {
			  busService.deleteBus(id);
			  return new ResponseEntity<String>("bus deleted successfully",HttpStatus.OK);
		  }
		  else {
			throw new UserNotAdmin("invalid admin");
		}
	  }

}
