package com.learn.busBooking.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learn.busBooking.Dto.BusDto;
import com.learn.busBooking.Dto.BusRouteDto;
import com.learn.busBooking.Dto.ResponseDto;
import com.learn.busBooking.exception.BusNotFoundException;
import com.learn.busBooking.model.Bus;
import com.learn.busBooking.repository.BusRepository;

@Service
public class BusService {
	@Autowired
	BusRepository busRepository;
	private ResponseDto responseDto = new ResponseDto();
	public ResponseDto createBus(BusDto busDto) {
		Bus bus = new Bus();
		BeanUtils.copyProperties(busDto, bus);
		bus=busRepository.save(bus);
		BeanUtils.copyProperties(bus, responseDto);
		return responseDto;
	}
	
	 public List<Bus> findBySourceAndDestinationAndDepatureDate(BusRouteDto busRouteDto) {
		  
		  List<Bus> buses =  busRepository.findBusBySourceAndDestinationAndDepartureDate(busRouteDto.getSource(), busRouteDto.getDestination(),busRouteDto.getDepatureDate());
		   if(buses.isEmpty()) {
			   throw new BusNotFoundException();
		   } else 
		   {
			   return buses;
		   }
		 }
		  public ResponseDto findBus(long id) {
			  Bus bus = new Bus();
			  bus=busRepository.findById(id).orElseThrow(()->new BusNotFoundException());
			  BeanUtils.copyProperties(bus, responseDto);
			  return responseDto;
			}
		 

		public List<Bus> findBySourceAndDestination(String source, String destination) {
			// TODO Auto-generated method stub
			return busRepository.findBySourceAndDestination(source, destination);
		}

	
		public ResponseDto updateBus(ResponseDto busDto) {
			Bus bus = new Bus();
			BeanUtils.copyProperties(busDto, bus);
			bus=busRepository.save(bus);
			BeanUtils.copyProperties(bus, responseDto);
			return responseDto;
		}
	
		public void deleteBus(long id) {
			if(busRepository.findById(id).isPresent()) {
				busRepository.deleteById(id);
			}else {
				throw new BusNotFoundException();
			}
		}
}
