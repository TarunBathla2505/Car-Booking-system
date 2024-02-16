package com.CabBookingBackend.Service;

import java.util.List;

import com.CabBookingBackend.Exception.CabException;
import com.CabBookingBackend.Exception.CurrentUserSessionException;
import com.CabBookingBackend.Exception.TripBookingException;
import com.CabBookingBackend.Model.Cab;
import com.CabBookingBackend.Model.TripBooking;
import com.CabBookingBackend.Model.TripBookingDTO;

public interface TripBookingService {

	List<Cab> searchByLocation(String pickUpLocation,String uuid)throws TripBookingException,CurrentUserSessionException;
	
	TripBooking BookRequest(Integer cabId,TripBooking tripBooking,String uuid) throws TripBookingException,CabException,CurrentUserSessionException;
	
	TripBooking AssignDriverByAdmin(Integer TripBookingId,String uuid)throws TripBookingException,CabException,CurrentUserSessionException;
	
	TripBookingDTO viewBookingById(Integer TripBookingId,String uuid )throws TripBookingException,CabException,CurrentUserSessionException;
	
	String MarkTripAsCompleted(Integer TripBookingId,String uuid)throws TripBookingException,CurrentUserSessionException;;
}
