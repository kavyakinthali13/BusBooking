package com.learn.busBooking.Dto;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class BusDto {
		private String source;
	    private String destination;
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date departureDate;
	    
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date arrivalDate;
	    private int availableTickets;
	    private int totalSeats;
	    
		public String getSource() {
			return source;
		}
		public void setSource(String source) {
			this.source = source;
		}
		public String getDestination() {
			return destination;
		}
		public void setDestination(String destination) {
			this.destination = destination;
		}
		
		public Date getDepartureDate() {
			return departureDate;
		}
		public void setDepartureDate(Date departureDate) {
			this.departureDate = departureDate;
		}
		public Date getArrivalDate() {
			return arrivalDate;
		}
		public void setArrivalDate(Date arrivalDate) {
			this.arrivalDate = arrivalDate;
		}
		public int getAvailableTickets() {
			return availableTickets;
		}
		public void setAvailableTickets(int availableTickets) {
			this.availableTickets = availableTickets;
		}
		public int getTotalSeats() {
			return totalSeats;
		}
		public void setTotalSeats(int totalSeats) {
			this.totalSeats = totalSeats;
		}
	    

}
